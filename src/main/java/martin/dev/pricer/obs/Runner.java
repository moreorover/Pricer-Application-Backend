package martin.dev.pricer.obs;

import martin.dev.pricer.data.service.StoreService;
import org.springframework.scheduling.annotation.Scheduled;

public class Runner {

    private StoreService storeService;
    private Subject subject;

    public Runner(StoreService storeService, Subject subject) {
        this.storeService = storeService;
        this.subject = subject;
    }

    @Scheduled(fixedRate = 15 * 1000, initialDelay = 5 * 1000)
    public void runner() {
        System.out.println("Started scheduled");

        this.storeService.fetchAllStores().forEach(store -> {
            store.getUrls().stream()
                    .forEach(url -> {
                        subject.setStoreAndUrl(store, url);
                        subject.notifyAllObservers();
                    });
        });
    }
}
