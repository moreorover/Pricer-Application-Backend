package martin.dev.pricer.obs.strategy;

import martin.dev.pricer.data.model.Status;
import martin.dev.pricer.data.model.Url;
import martin.dev.pricer.data.service.StoreService;
import org.springframework.scheduling.annotation.Scheduled;

public class Launcher {

    private StoreService storeService;
    private ScraperSubject scraperSubject;

    public Launcher(StoreService storeService, ScraperSubject scraperSubject) {
        this.storeService = storeService;
        this.scraperSubject = scraperSubject;
    }

    @Scheduled(fixedRate = 60 * 1000, initialDelay = 5 * 1000)
    public void runner() {
        System.out.println("Started scheduled");

        this.storeService.fetchAllStores().forEach(store -> {
            store.getUrls().stream()
                    .filter(Url::isReadyToScrape)
                    .forEach(url -> {
                        this.storeService.updateUrlStatus(store, url, Status.SCRAPING);
                        scraperSubject.setStoreAndUrl(store, url);
                        scraperSubject.notifyAllObservers();
                        this.storeService.updateUrlLastTimeChecked(store, url);
                        this.storeService.updateUrlStatus(store, url, Status.READY);
                    });
        });
    }
}
