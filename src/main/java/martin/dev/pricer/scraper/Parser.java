package martin.dev.pricer.scraper;

import martin.dev.pricer.data.model.store.StoreUrl;
import martin.dev.pricer.data.services.store.StoreUrlHandler;
import martin.dev.pricer.scraper.parser.ernestjones.ErnestJonesParserProcessor;
import martin.dev.pricer.scraper.parser.hsamuel.HSamuelParserProcessor;
import org.springframework.scheduling.annotation.Scheduled;

public class Parser {

    private StoreUrlHandler storeUrlHandler;
    private HSamuelParserProcessor hSamuelParserProcessor;
    private ErnestJonesParserProcessor ernestJonesParserProcessor;

    public Parser(StoreUrlHandler storeUrlHandler, HSamuelParserProcessor hSamuelParserProcessor, ErnestJonesParserProcessor ernestJonesParserProcessor) {
        this.storeUrlHandler = storeUrlHandler;
        this.hSamuelParserProcessor = hSamuelParserProcessor;
        this.ernestJonesParserProcessor = ernestJonesParserProcessor;
    }

    @Scheduled(fixedRate = 30000, initialDelay = 5000)
    public void parse() {
        StoreUrl storeUrl;
        try {
            storeUrl = storeUrlHandler.fetchUrlToScrape(0, 2, 0);

            if (storeUrl.getStore().getName().equals("H. Samuel")) {

                storeUrlHandler.setStatusScraping(storeUrl);

                hSamuelParserProcessor.scrapePages(storeUrl);
            } else if (storeUrl.getStore().getName().equals("Ernest Jones")){
                storeUrlHandler.setStatusScraping(storeUrl);

                ernestJonesParserProcessor.scrapePages(storeUrl);
            }

            storeUrlHandler.setStatusReady(storeUrl);
            storeUrlHandler.setLastCheckedTimeToNow(storeUrl);
        } catch (NullPointerException ignored) {
        }
    }
}
