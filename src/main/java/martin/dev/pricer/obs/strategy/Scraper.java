package martin.dev.pricer.obs.strategy;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.service.ItemService;
import martin.dev.pricer.obs.Observer;
import martin.dev.pricer.scraper.client.HttpClient;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;

@Slf4j
public class Scraper extends Observer implements ScraperInterface {

    private ScraperSubject scraperSubject;

    private ParserHandler parserHandler;
    private ItemService itemService;
    private int startPage;


    public Scraper(ScraperSubject scraperSubject, ParserHandler parserHandler, ItemService itemService, int startPage) {
        this.scraperSubject = scraperSubject;
        this.scraperSubject.attach(this);
        this.parserHandler = parserHandler;
        this.itemService = itemService;
        this.startPage = startPage;
    }

    @Override
    public void scrape(int endPage) {
        for (int start = startPage; start <= endPage; start++) {
            String url = parserHandler.makeUrl(scraperSubject.getUrl().getUrl(), start);
            log.info("Parsing: " + url);
            Document document = HttpClient.readContentInJsoupDocument(url);
            Elements parsedElements = parserHandler.parseItems(document);
            List<ParsedItemDto> parsedItemModels = parserHandler.parseItemModels(parsedElements, scraperSubject.getUrl().getUrl());
            parsedItemModels.forEach(parsedItemDto -> itemService.processParsedItem(parsedItemDto, scraperSubject.getStore(), scraperSubject.getUrl()));
        }
    }

    @Override
    public void update() {

        int maxPageNum = getMaxPage();
        scrape(maxPageNum);
    }

    @Override
    public String getName() {
        return parserHandler.getParserName();
    }

    @Override
    public int getMaxPage() {
        Document document = HttpClient.readContentInJsoupDocument(scraperSubject.getUrl().getUrl());
        int maxPage = parserHandler.parseMaxPageNum(document);

        if (startPage == 0) {
            return maxPage - 1;
        }
        return maxPage;
    }
}
