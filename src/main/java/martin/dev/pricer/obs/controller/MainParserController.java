package martin.dev.pricer.obs.controller;

import martin.dev.pricer.data.model.Status;
import martin.dev.pricer.data.model.Store;
import martin.dev.pricer.data.model.Url;
import martin.dev.pricer.data.service.StoreService;

import java.util.Map;

public class MainParserController {

    private StoreService storeService;
    private Map<String, StoreController> storeControllerMap;

    private Store store;

    public MainParserController(Store store) {
        this.store = store;
    }

    public void launch() {
        store.getUrls().stream()
                .filter(Url::isReadyToScrape)
                .forEach(url -> {
                    this.storeService.updateUrlStatus(store, url, Status.SCRAPING);
                    StoreController storeController = storeControllerMap.get(store.getName());

                });
    }
}
