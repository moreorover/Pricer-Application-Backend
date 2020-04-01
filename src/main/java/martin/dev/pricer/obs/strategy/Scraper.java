package martin.dev.pricer.obs.strategy;

import martin.dev.pricer.data.model.Store;
import martin.dev.pricer.data.model.Url;
import martin.dev.pricer.data.service.ItemService;
import martin.dev.pricer.obs.controller.ParsedItemModel;
import martin.dev.pricer.scraper.client.HttpClient;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;

public abstract class Scraper implements ScraperInterface {

    private ParserHandler parserHandler;
    private ItemService itemService;

    private Store store;
    private Url url;

    public Scraper(ParserHandler parserHandler, ItemService itemService) {
        this.parserHandler = parserHandler;
        this.itemService = itemService;
    }

    public void scrape(Store store, Url url){
        this.store = store;
        this.url = url;
    }

    public void pp(int startPage, int endPage) {
        int currentRotation = 0;

        for (startPage, startPage <= endPage, startPage++) {
            Document document = HttpClient.readContentInJsoupDocument(parserHandler.makeUrl(url.getUrl(), currentRotation));
            Elements parsedElements = parserHandler.parseItems(document);
            List<ParsedItemModel> parsedItemModels = parserHandler.parseItemModels(parsedElements, url.getUrl());
            parsedItemModels.forEach(parsedItemDto -> itemService.processParsedItem(parsedItemDto, store, url));
        }
    }
}
