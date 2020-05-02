package martin.dev.pricer.scraper.flyway;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.flyway.service.ItemServiceFlyway;
import martin.dev.pricer.scraper.Observer;
import martin.dev.pricer.scraper.ParserHandler;
import martin.dev.pricer.scraper.ScraperInterface;
import martin.dev.pricer.scraper.client.HttpClient;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;

@Slf4j
public class Scraper extends Observer implements ScraperInterface {

    private ScraperSubject scraperSubject;

    private ParserHandler parserHandler;
    private ItemServiceFlyway itemServiceFlyway;
    private int startPage;

    public Scraper(ScraperSubject scraperSubject, ParserHandler parserHandler, ItemServiceFlyway itemServiceFlyway, int startPage) {
        this.scraperSubject = scraperSubject;
        this.scraperSubject.attach(this);
        this.parserHandler = parserHandler;
        this.itemServiceFlyway = itemServiceFlyway;
        this.startPage = startPage;
    }

    @Override
    public void scrape(int endPage) {
        for (int start = startPage; start <= endPage; start++) {
            String url = parserHandler.makeUrl(scraperSubject.getUrl().getUrl(), start);
            this.parserHandler.setCurrentUrl(url);
            log.info("Parsing: " + url);
            Document document = HttpClient.readContentInJsoupDocument(url);
            Elements parsedElements = parserHandler.parseItems(document);
            List<ParsedItemDto> parsedItemModels = parserHandler.parseItemModels(parsedElements, scraperSubject.getUrl());
            parsedItemModels.forEach(parsedItemDto -> itemServiceFlyway.processParsedItemDto(parsedItemDto));
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
