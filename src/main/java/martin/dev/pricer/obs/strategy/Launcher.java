package martin.dev.pricer.obs.strategy;

import martin.dev.pricer.data.service.StoreService;
import martin.dev.pricer.obs.Subject;
import org.springframework.scheduling.annotation.Scheduled;

public class Launcher {

    private StoreService storeService;
    private Subject subject;

    public Launcher(StoreService storeService) {
        this.storeService = storeService;
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
