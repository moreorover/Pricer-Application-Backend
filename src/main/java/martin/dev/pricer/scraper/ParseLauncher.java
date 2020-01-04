package martin.dev.pricer.scraper;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.fabric.product.DealProcessor;
import martin.dev.pricer.data.model.store.StoreUrl;
import martin.dev.pricer.data.services.store.StoreUrlHandler;
import martin.dev.pricer.scraper.parser.*;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Arrays;

@Slf4j
public class ParseLauncher {

    private StoreUrlHandler storeUrlHandler;
    private DealProcessor dealProcessor;
    //    private ScraperImplementation scraperImplementation = new ScraperImplementation();

    public ParseLauncher(StoreUrlHandler storeUrlHandler, DealProcessor dealProcessor) {
        this.storeUrlHandler = storeUrlHandler;
        this.dealProcessor = dealProcessor;
    }

    @Scheduled(fixedRate = 60000, initialDelay = 1000)
    public void parse() {
        StoreUrl storeUrl = storeUrlHandler.fetchUrlToScrape(0, 2, 0);

        if (storeUrl == null) {
            log.info("Nothing in database, will check again in 60 seconds.");
            return;
        }
        try {
            storeUrlHandler.setStatusScraping(storeUrl);
            switch (storeUrl.getStore().getName()) {
                case "H. Samuel":
                case "Ernest Jones":
                    Scraper HSamuelScraper = new HSamuelScraper(storeUrl, this.dealProcessor);
                    HSamuelScraper.scrapePages();
                    break;
                case "Superdrug":
                    Scraper SuperDrugScraper = new SuperDrugScraper(storeUrl, this.dealProcessor);
                    SuperDrugScraper.scrapePages();
                    break;
                case "Argos":
                    Scraper ArgosScraper = new ArgosScraper(storeUrl, this.dealProcessor);
                    ArgosScraper.scrapePages();
                    break;
//                case "All Beauty":
//                    Scraper AllBeautyScraper = new AllBeautyScraper();
//                    AllBeautyScraper.scrapePages(storeUrl);
//                    break;
                case "AMJ Watches":
                    Scraper AMJWatchesScraper = new AMJWatchesScraper(storeUrl, this.dealProcessor);
                    AMJWatchesScraper.scrapePages();
                    break;
                case "Debenhams":
                    Scraper DebenhamsScraper = new DebenhamsScraper(storeUrl, this.dealProcessor);
                    DebenhamsScraper.scrapePages();
                    break;
                case "Creation Watches":
                    Scraper CreationWatchesScraper = new CreationWatchesScraper(storeUrl, this.dealProcessor);
                    CreationWatchesScraper.scrapePages();
                    break;
                case "Watcho":
                    Scraper WatchoScraper = new WatchoScraper(storeUrl, this.dealProcessor);
                    WatchoScraper.scrapePages();
                    break;
            }
        } catch (Exception e) {
            log.error(Arrays.toString(e.getStackTrace()));
        } finally {
            storeUrlHandler.setStatusReady(storeUrl);
            storeUrlHandler.setLastCheckedTimeToNow(storeUrl);
        }
    }
}
