package martin.dev.pricer.scraper;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.model.Category;
import martin.dev.pricer.data.model.Status;
import martin.dev.pricer.data.model.Store;
import martin.dev.pricer.data.model.Url;
import martin.dev.pricer.data.service.ItemServiceI;
import martin.dev.pricer.data.service.StoreService;
import martin.dev.pricer.scraper.parser.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class ParseLauncher {

    private StoreService storeService;
    @Autowired
    private ItemServiceI itemService;

    public ParseLauncher(StoreService storeService) {
        this.storeService = storeService;
    }

    //    @Scheduled(fixedRate = 600000000, initialDelay = 1)
    public void insertNewStore() {

        HashMap<String, Set<Category>> data = new HashMap<>();

        data.put("https://www.goldsmiths.co.uk/c/Watches/Ladies-Watches/filter/Page_1/Psize_96/Show_Page/", Stream.of("Women", "Watch").map(Category::new).collect(Collectors.toSet()));
        data.put("https://www.goldsmiths.co.uk/c/Watches/Mens-Watches/filter/Page_1/Psize_96/Show_Page/", Stream.of("Men", "Watch").map(Category::new).collect(Collectors.toSet()));
//        data.put("", Stream.of("Technology", "Gaming", "Console", "PS4").map(Category::new).collect(Collectors.toSet()));
//        data.put("", Stream.of("Technology", "Gaming", "Console", "Xbox").map(Category::new).collect(Collectors.toSet()));
//        data.put("", Stream.of("Technology", "Gaming", "Console", "Nintendo").map(Category::new).collect(Collectors.toSet()));
//        data.put("", Stream.of("Technology", "Headphones").map(Category::new).collect(Collectors.toSet()));
//        data.put("", Stream.of("Technology", "Smart").map(Category::new).collect(Collectors.toSet()));
//        data.put("", Stream.of("Technology", "Smart").map(Category::new).collect(Collectors.toSet()));
//        data.put("", Stream.of("Technology", "Smart").map(Category::new).collect(Collectors.toSet()));
//        data.put("", Stream.of("Technology", "Smart").map(Category::new).collect(Collectors.toSet()));
//        data.put("", Stream.of("Technology", "Smart").map(Category::new).collect(Collectors.toSet()));
//        data.put("", Stream.of("Women", "Fragrance").map(Category::new).collect(Collectors.toSet()));
//        data.put("", Stream.of("Men", "Fragrance").map(Category::new).collect(Collectors.toSet()));

        Set<Url> urlList = new HashSet<>();

        data.keySet().forEach(urlKey -> urlList.add(new Url(urlKey, LocalDateTime.now(), Status.READY, data.get(urlKey))));

        Store store = new Store("Gold Smiths", "https://www.goldsmiths.co.uk", "https://lh3.googleusercontent.com/-Ck01XdjIITQ/AAAAAAAAAAI/AAAAAAAAA9o/iz6zLZRdIZ0/s250-c/photo.jpg", urlList);

        this.storeService.getStoreRepository().save(store);

        System.out.println("finished");

    }

    @Scheduled(fixedRate = 60000, initialDelay = 1)
    public void parse() {
        List<Store> storeList = this.storeService.fetchStoresToScrape();

//        assertTrue(storeList.size() > 0, "No Stores found!");

        if (storeList.size() == 0) {
            log.info("Nothing to scrape.");
        }

        storeList.forEach(store -> store.getUrlsToScrape().forEach(url -> {
            this.storeService.updateUrlStatus(store, url, Status.SCRAPING);

            switch (store.getName()) {
                case "Creation Watches":
                    new Scraper<ItemServiceI, Parser>(this.itemService, new CreationWatchesParser(), store, url).scrapePagesFromOne();
                    break;
                case "First Class Watches":
                    new Scraper<ItemServiceI, Parser>(this.itemService, new FirstClassWatchesParser(), store, url).scrapePagesFromOne();
                    break;
                case "AMJ Watches":
                    new Scraper<ItemServiceI, Parser>(this.itemService, new AMJWatchesParser(), store, url).scrapePagesFromOne();
                    break;
                case "Argos":
                    new Scraper<ItemServiceI, Parser>(this.itemService, new ArgosParser(), store, url).scrapePagesFromOne();
                    break;
                case "Superdrug":
                    new Scraper<ItemServiceI, Parser>(this.itemService, new SuperDrugParser(), store, url).scrapePagesFromZero();
                    break;
                case "H. Samuel":
                    new Scraper<ItemServiceI, Parser>(this.itemService, new HSamuelParser(), store, url).scrapePagesFromOne();
                    break;
                case "Ernest Jones":
                    new Scraper<ItemServiceI, Parser>(this.itemService, new ErnestJonesParser(), store, url).scrapePagesFromOne();
                    break;
                case "Debenhams":
                    new Scraper<ItemServiceI, Parser>(this.itemService, new DebenhamsParser(), store, url).scrapePagesFromOne();
                    break;
                case "Watch Shop":
                    new Scraper<ItemServiceI, Parser>(this.itemService, new WatchShopParser(), store, url).scrapePagesFromOne();
                    break;
                case "Gold Smiths":
                    new Scraper<ItemServiceI, Parser>(this.itemService, new GoldSmithsParser(), store, url).scrapePagesFromOne();
                    break;
            }

            this.storeService.updateUrlLastTimeChecked(store, url);
            this.storeService.updateUrlStatus(store, url, Status.READY);
        }));
    }
}
