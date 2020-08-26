package martin.dev.pricer.scraper;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.service.ItemService;
import martin.dev.pricer.scraper.client.HttpClient;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Scraper extends Observer implements ScraperInterface {

    private ScraperSubject scraperSubject;

    private AbstractParser parser;
    private ItemService itemService;

    public Scraper(ScraperSubject scraperSubject, AbstractParser parser, ItemService itemService) {
        this.scraperSubject = scraperSubject;
        this.scraperSubject.attach(this);
        this.parser = parser;
        this.itemService = itemService;
    }

    @Override
    public void update() {
        this.parser.setUrlObject(scraperSubject.getUrl());
        List<String> urlsToScrape = fetchUrlsToScrape();
        scrapeUrls(urlsToScrape);
    }

    @Override
    public String getName() {
        return parser.getNAME();
    }

    public List<String> fetchUrlsToScrape() {
        parser.setCurrentPageUrl(scraperSubject.getUrl().getUrl());
        List<String> urlsToScrape = new ArrayList<>();
        Document document = HttpClient.readContentInJsoupDocument(scraperSubject.getUrl().getUrl());
        parser.setDocument(document);
        parser.parseMaxPageNum();
        for (int page_number = parser.getSTART_PAGE_NUMBER(); page_number <= parser.getMAX_PAGE_NUMBER(); page_number++) {
            String url = parser.makeNextPageUrl(page_number);
            urlsToScrape.add(url);
        }
        return urlsToScrape;
    }

    public void scrapeUrls(List<String> urlsToScrape) {
        urlsToScrape.forEach(url -> {
            parser.setCurrentPageUrl(url);
            log.info("Parsing: " + url);
            Document document = HttpClient.readContentInJsoupDocument(url);
            parser.setDocument(document);
            parser.parseListOfAdElements();
            List<ParsedItemDto> parsedItemModels = parser.parseItemModels();
            parsedItemModels.forEach(parsedItemDto -> itemService.processParsedItemDto(parsedItemDto));
        });
    }
}
