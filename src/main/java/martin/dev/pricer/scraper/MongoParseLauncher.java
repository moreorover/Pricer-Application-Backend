package martin.dev.pricer.scraper;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.model.mongo.model.Category;
import martin.dev.pricer.data.model.mongo.model.Status;
import martin.dev.pricer.data.model.mongo.model.Store;
import martin.dev.pricer.data.model.mongo.model.Url;
import martin.dev.pricer.data.model.mongo.service.MongoItemService;
import martin.dev.pricer.data.model.mongo.service.MongoStoreService;
import martin.dev.pricer.scraper.parser.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class MongoParseLauncher {

    private MongoStoreService mongoStoreService;
    @Autowired
    private MongoItemService itemService;

    public MongoParseLauncher(MongoStoreService mongoStoreService) {
        this.mongoStoreService = mongoStoreService;
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

        data.keySet().forEach(urlKey -> {
            urlList.add(new Url(urlKey, LocalDateTime.now(), Status.READY, data.get(urlKey)));
        });

        Store store = new Store("Gold Smiths", "https://www.goldsmiths.co.uk", "https://lh3.googleusercontent.com/-Ck01XdjIITQ/AAAAAAAAAAI/AAAAAAAAA9o/iz6zLZRdIZ0/s250-c/photo.jpg", urlList);

        this.mongoStoreService.getMongoStoreRepository().save(store);

        System.out.println("finished");

    }

    @Scheduled(fixedRate = 60000, initialDelay = 1)
    public void parse() {
        List<Store> storeList = this.mongoStoreService.fetchStoresToScrape();

//        assertTrue(storeList.size() > 0, "No Stores found!");

        if (storeList.size() == 0){
            log.info("Nothing to scrape.");
        }

        storeList.forEach(store -> {
            store.getUrlsToScrape().forEach(url -> {
                this.mongoStoreService.updateUrlStatus(store, url, Status.SCRAPING);

                switch (store.getName()) {
                    case "Creation Watches":
                        new MongoScraperNEW<MongoItemService, ParserMongo>(this.itemService, new CreationWatchesParser(), store, url).scrapePagesFromOne();
                        break;
                    case "First Class Watches":
                        new MongoScraperNEW<MongoItemService, ParserMongo>(this.itemService, new FirstClassWatchesParser(), store, url).scrapePagesFromOne();
                        break;
                    case "AMJ Watches":
                        new MongoScraperNEW<MongoItemService, ParserMongo>(this.itemService, new AMJWatchesParser(), store, url).scrapePagesFromOne();
                        break;
                    case "Argos":
                        new MongoScraperNEW<MongoItemService, ParserMongo>(this.itemService, new ArgosParser(), store, url).scrapePagesFromOne();
                        break;
                    case "Superdrug":
                        new MongoScraperNEW<MongoItemService, ParserMongo>(this.itemService, new SuperDrugParser(), store, url).scrapePagesFromZero();
                        break;
                    case "H. Samuel":
                    case "Ernest Jones":
                        new MongoScraperNEW<MongoItemService, ParserMongo>(this.itemService, new HSamuelParser(), store, url).scrapePagesFromOne();
                        break;
                    case "Debenhams":
                        new MongoScraperNEW<MongoItemService, ParserMongo>(this.itemService, new DebenhamsParser(), store, url).scrapePagesFromOne();
                        break;
                    case "Watch Shop":
                        new MongoScraperNEW<MongoItemService, ParserMongo>(this.itemService, new WatchShopParser(), store, url).scrapePagesFromOne();
                        break;
                    case "Gold Smiths":
                        new MongoScraperNEW<MongoItemService, ParserMongo>(this.itemService, new GoldSmithsParser(), store, url).scrapePagesFromOne();
                        break;
                }

                this.mongoStoreService.updateUrlLastTimeChecked(store, url);
                this.mongoStoreService.updateUrlStatus(store, url, Status.READY);
            });
        });
    }
}
