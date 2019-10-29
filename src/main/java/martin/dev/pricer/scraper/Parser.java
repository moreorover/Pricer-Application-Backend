package martin.dev.pricer.scraper;

import martin.dev.pricer.data.model.store.StoreUrl;
import martin.dev.pricer.data.services.store.StoreUrlHandler;
import martin.dev.pricer.scraper.parser.hsamuel.HSamuelParser;

public class Parser {

    private StoreUrlHandler storeUrlHandler;

    public Parser(StoreUrlHandler storeUrlHandler) {
        this.storeUrlHandler = storeUrlHandler;
    }

    public void parse(long days, long hours, long minutes) {
        StoreUrl storeUrl;
        try {
            storeUrl = storeUrlHandler.fetchUrlToScrape(days, hours, minutes);

            if (storeUrl.getStore().getName().equals("H. Samuel")) {

                storeUrlHandler.setStatusScraping(storeUrl);

                HSamuelParser hSamuelParser = new HSamuelParser(itemRepository, priceRepository, storeUrlRepository, storeUrl);

                hSamuelParser.parse();
            }

            storeUrlHandler.setStatusReady(storeUrl);
        } catch (NullPointerException ignored) {
        }
    }
}
