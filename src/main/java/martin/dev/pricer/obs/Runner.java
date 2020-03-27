package martin.dev.pricer.obs;

import martin.dev.pricer.data.model.Url;
import martin.dev.pricer.data.service.StoreService;
import org.springframework.scheduling.annotation.Scheduled;

public class Runner {

    private StoreService storeService;

    Subject subject = new Subject();
    HSamuelObserver hSamuelObserver = new HSamuelObserver(subject);

    public Runner(StoreService storeService) {
        this.storeService = storeService;
    }

    @Scheduled(fixedRate = 15 * 1000, initialDelay = 5 * 1000)
    public void runner() {
        System.out.println("Started scheduled");

        this.storeService.fetchAllStores().forEach(store -> {
            store.getUrls().stream()
                    .filter(Url::isReadyToScrape)
                    .forEach(url -> {
                        subject.setStoreAndUrl(store, url);
                    });
        });
    }
}