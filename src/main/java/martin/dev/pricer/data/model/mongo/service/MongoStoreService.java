package martin.dev.pricer.data.model.mongo.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.model.mongo.model.Status;
import martin.dev.pricer.data.model.mongo.model.Store;
import martin.dev.pricer.data.model.mongo.model.Url;
import martin.dev.pricer.data.model.mongo.repository.MongoStoreRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public List<Store> fetchUrlsToScrape(){
        return this.getMongoStoreRepository().findAll();
    }

    public List<Store> fetchUrlsToScrapeOld() {

        List<Store> stores = this.getMongoStoreRepository().findAll().stream()
                .filter(store -> store.getUrls().stream().anyMatch(Url::isReadyToScrape))
                .collect(Collectors.toList());

        return stores;

//        HashMap<Store, List<Url>> urlsToScrape = new HashMap<>();
//
//        this.getMongoStoreRepository().findAll().forEach(store -> {
//
//            List<Url> tempUrlList = store.getUrls().stream()
//                    .filter(Url::isReadyToScrape)
//                    .collect(Collectors
//                            .toCollection(ArrayList::new));
//
//            if (tempUrlList.size() != 0) {
//                urlsToScrape.put(store, tempUrlList);
//            }
//        });
//        return urlsToScrape;
    }
}
