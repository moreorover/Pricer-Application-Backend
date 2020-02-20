package martin.dev.pricer.data.model.mongo.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.model.mongo.model.Status;
import martin.dev.pricer.data.model.mongo.model.Store;
import martin.dev.pricer.data.model.mongo.model.Url;
import martin.dev.pricer.data.model.mongo.repository.MongoStoreRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Getter
public class MongoStoreService {

    private MongoStoreRepository mongoStoreRepository;

    public MongoStoreService(MongoStoreRepository mongoStoreRepository) {
        this.mongoStoreRepository = mongoStoreRepository;
    }

    public void updateUrlStatus(Store store, Url url, Status status) {

        store.getUrls().forEach(storeUrl -> {
            if (storeUrl.equals(url)) {
                storeUrl.updateStatusTo(status);
            }
        });
        this.getMongoStoreRepository().save(store);
    }

    public void updateUrlLastTimeChecked(Store store, Url url) {
        store.getUrls().forEach(storeUrl -> {
            if (storeUrl.equals(url)) {
                storeUrl.updateLastCheckedToNow();
            }
        });
        this.getMongoStoreRepository().save(store);
    }

    public List<Store> fetchStoresToScrape() {
        return this.getMongoStoreRepository().findAll().stream()
                .filter(Store::filterUrlsToScrape)
                .collect(Collectors.toList());
    }
}
