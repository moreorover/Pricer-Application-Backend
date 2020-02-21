package martin.dev.pricer.data.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.model.Status;
import martin.dev.pricer.data.model.Store;
import martin.dev.pricer.data.model.Url;
import martin.dev.pricer.data.repository.StoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Getter
public class StoreService {

    private StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public void updateUrlStatus(Store store, Url url, Status status) {

        store.getUrls().forEach(storeUrl -> {
            if (storeUrl.equals(url)) {
                storeUrl.updateStatusTo(status);
            }
        });
        this.getStoreRepository().save(store);
    }

    public void updateUrlLastTimeChecked(Store store, Url url) {
        store.getUrls().forEach(storeUrl -> {
            if (storeUrl.equals(url)) {
                storeUrl.updateLastCheckedToNow();
            }
        });
        this.getStoreRepository().save(store);
    }

    public List<Store> fetchStoresToScrape() {
        return this.getStoreRepository().findAll().stream()
                .filter(Store::filterUrlsToScrape)
                .collect(Collectors.toList());
    }
}
