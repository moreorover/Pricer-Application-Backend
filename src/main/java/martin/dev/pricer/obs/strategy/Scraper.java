package martin.dev.pricer.obs.strategy;

import martin.dev.pricer.data.model.Store;
import martin.dev.pricer.data.model.Url;
import martin.dev.pricer.scraper.client.HttpClient;
import org.jsoup.nodes.Document;

public class Scraper implements ScraperInterface {

    private ParserHandler parserHandler;
    private ScrapeEngine scrapeEngine;

    private Store store;
    private Url url;

    public Scraper(ParserHandler parserHandler, ScrapeEngine scrapeEngine) {
        this.parserHandler = parserHandler;
        this.scrapeEngine = scrapeEngine;
    }

    public void scrape(Store store, Url url){
        this.store = store;
        this.url = url;
    }

    public Document getContent(String urlLink){
        return HttpClient.readContentInJsoupDocument(urlLink);
    }

}
