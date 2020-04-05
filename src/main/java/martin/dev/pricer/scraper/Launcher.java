package martin.dev.pricer.scraper;

import martin.dev.pricer.data.model.Category;
import martin.dev.pricer.data.model.Status;
import martin.dev.pricer.data.model.Store;
import martin.dev.pricer.data.model.Url;
import martin.dev.pricer.data.service.StoreService;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

//    @Scheduled(fixedRate = 600000000, initialDelay = 1)
    public void insertNewStore() {

        HashMap<String, Set<Category>> data = new HashMap<>();

        data.put("https://www.watcho.co.uk/watches/seiko.html?page=1", Stream.of("Watch").map(Category::new).collect(Collectors.toSet()));
        data.put("https://www.watcho.co.uk/watches/garmin.html?page=1", Stream.of("Watch").map(Category::new).collect(Collectors.toSet()));
        data.put("https://www.watcho.co.uk/watches/casio/casio-g-shock-watches.html?page=1", Stream.of("Watch").map(Category::new).collect(Collectors.toSet()));
        data.put("https://www.watcho.co.uk/watches/citizen.html?page=1", Stream.of("Watch").map(Category::new).collect(Collectors.toSet()));

        Set<Url> urlList = new HashSet<>();

        data.keySet().forEach(urlKey -> urlList.add(new Url(urlKey, LocalDateTime.now(), Status.READY, data.get(urlKey))));

        Store store = new Store("Watcho", "https://www.watcho.co.uk", "https://cdn11.bigcommerce.com/s-f06f69/images/stencil/250x100/final-logo-small_1571639906__21982.original.png", urlList);

        this.storeService.getStoreRepository().save(store);

        System.out.println("finished");

    }
}
