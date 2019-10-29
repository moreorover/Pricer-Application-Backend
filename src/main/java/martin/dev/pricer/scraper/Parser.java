package martin.dev.pricer.scraper;

import martin.dev.pricer.data.model.store.StoreUrl;
import martin.dev.pricer.data.services.store.StoreUrlHandler;
import martin.dev.pricer.scraper.parser.hsamuel.HSamuelParserProcessor;

public class Parser {

    private StoreUrlHandler storeUrlHandler;
    private HSamuelParserProcessor hSamuelParserProcessor;

    public Parser(StoreUrlHandler storeUrlHandler, HSamuelParserProcessor hSamuelParserProcessor1) {
        this.storeUrlHandler = storeUrlHandler;
        this.hSamuelParserProcessor = hSamuelParserProcessor1;
    }

    public void parse(long days, long hours, long minutes) {
        StoreUrl storeUrl;
        try {
            storeUrl = storeUrlHandler.fetchUrlToScrape(days, hours, minutes);

            if (storeUrl.getStore().getName().equals("H. Samuel")) {

                storeUrlHandler.setStatusScraping(storeUrl);

                hSamuelParserProcessor.scrapePages(storeUrl);
            }

            storeUrlHandler.setStatusReady(storeUrl);
            storeUrlHandler.setLastCheckedTimeToNow(storeUrl);
        } catch (NullPointerException ignored) {
        }
    }
}
