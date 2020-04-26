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

        data.put("https://simpkinsjewellers.co.uk/watches/seiko?limit=100&page=1", Stream.of("Watch").map(Category::new).collect(Collectors.toSet()));
        data.put("https://simpkinsjewellers.co.uk/watches/accurist?limit=100&page=1", Stream.of("Watch").map(Category::new).collect(Collectors.toSet()));
        data.put("https://simpkinsjewellers.co.uk/watches/Mondaine?limit=100&page=1", Stream.of("Watch").map(Category::new).collect(Collectors.toSet()));
        data.put("https://simpkinsjewellers.co.uk/watches/AVI-8%20Aviator%20Watches?limit=100&page=1", Stream.of("Watch").map(Category::new).collect(Collectors.toSet()));
        data.put("https://simpkinsjewellers.co.uk/watches/police?limit=100&page=1", Stream.of("Watch").map(Category::new).collect(Collectors.toSet()));
        data.put("https://simpkinsjewellers.co.uk/watches/bering?limit=100&page=1", Stream.of("Watch").map(Category::new).collect(Collectors.toSet()));
        data.put("https://simpkinsjewellers.co.uk/watches/pulsar?limit=100&page=1", Stream.of("Watch").map(Category::new).collect(Collectors.toSet()));
        data.put("https://simpkinsjewellers.co.uk/watches/rotary?limit=100&page=1", Stream.of("Watch").map(Category::new).collect(Collectors.toSet()));
        data.put("https://simpkinsjewellers.co.uk/watches/casio?limit=100&page=1", Stream.of("Watch").map(Category::new).collect(Collectors.toSet()));
        data.put("https://simpkinsjewellers.co.uk/watches/citizen?limit=100&page=1", Stream.of("Watch").map(Category::new).collect(Collectors.toSet()));
        data.put("https://simpkinsjewellers.co.uk/watches/victorinox?limit=100&page=1", Stream.of("Watch").map(Category::new).collect(Collectors.toSet()));
        data.put("https://simpkinsjewellers.co.uk/watches/lorus?limit=100&page=1", Stream.of("Watch").map(Category::new).collect(Collectors.toSet()));
        data.put("https://simpkinsjewellers.co.uk/watches/bulova?limit=100&page=1", Stream.of("Watch").map(Category::new).collect(Collectors.toSet()));

        Set<Url> urlList = new HashSet<>();

        data.keySet().forEach(urlKey -> urlList.add(new Url(urlKey, LocalDateTime.now().minusHours(10), Status.READY, data.get(urlKey))));

        Store store = new Store("Simpkins Jewellers", "https://simpkinsjewellers.co.uk", "https://simpkinsjewellers.co.uk/image/catalog/Banners/Simpkins%20Logo%202.png", urlList);

        this.storeService.getStoreRepository().save(store);

        System.out.println("finished");

    }
}
