package martin.dev.pricer.data.services.store;

import martin.dev.pricer.data.model.store.Status;
import martin.dev.pricer.data.model.store.StoreUrl;

import java.time.LocalDateTime;

public class StoreUrlHandler {

    private StoreUrlRepository storeUrlRepository;

    public StoreUrlHandler(StoreUrlRepository storeUrlRepository) {
        this.storeUrlRepository = storeUrlRepository;
    }

    public StoreUrl fetchUrlToScrape(long days, long hours, long minutes){
        LocalDateTime timePast = LocalDateTime.now().minusDays(days).minusHours(hours).minusMinutes(minutes);
        return storeUrlRepository.findFirstByLastCheckedBeforeAndStatusOrderByLastCheckedAsc(timePast, Status.READY);
    }

    public void setStatusScraping(StoreUrl storeUrl){
        storeUrl.setStatus(Status.SCRAPING);
        storeUrlRepository.save(storeUrl);
    }

    public void setStatusReady(StoreUrl storeUrl){
        storeUrl.setStatus(Status.READY);
        storeUrlRepository.save(storeUrl);
    }

    public void setLastCheckedTimeToNow(StoreUrl storeUrl){
        storeUrl.setLastChecked(LocalDateTime.now());
        storeUrlRepository.save(storeUrl);
    }
}
