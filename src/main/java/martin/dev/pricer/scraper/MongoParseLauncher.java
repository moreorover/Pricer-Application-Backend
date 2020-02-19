package martin.dev.pricer.scraper;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.model.mongo.model.Category;
import martin.dev.pricer.data.model.mongo.model.Status;
import martin.dev.pricer.data.model.mongo.model.Store;
import martin.dev.pricer.data.model.mongo.model.Url;
import martin.dev.pricer.data.model.mongo.repository.MongoItemRepository;
import martin.dev.pricer.data.model.mongo.service.MongoStoreService;
import martin.dev.pricer.scraper.parser.MongoCreationWatchesScraper;
import martin.dev.pricer.scraper.parser.MongoFirstClassWatchesScraper;
import martin.dev.pricer.scraper.parser.MongoScraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;

@Slf4j
public class MongoParseLauncher {

    private MongoStoreService mongoStoreService;
    @Autowired
    private MongoItemRepository mongoItemRepository;

    public MongoParseLauncher(MongoStoreService mongoStoreService) {
        this.mongoStoreService = mongoStoreService;
    }

    //    @Scheduled(fixedRate = 600000000, initialDelay = 1000)
    public void insertNewStore() {
        Set<Category> categories = new HashSet<>();
        categories.add(new Category("Watch"));

        Set<Url> urls = new HashSet<>();
        urls.add(new Url("https://www.creationwatches.com/products/seiko-75/index-1-5d.html?currency=GBP",
                LocalDateTime.now(),
                Status.READY,
                categories));
        urls.add(new Url("https://www.creationwatches.com/products/tissot-247/index-1-5d.html?currency=GBP",
                LocalDateTime.now(),
                Status.READY,
                categories));
        urls.add(new Url("https://www.creationwatches.com/products/bulova-watches-271/index-1-5d.html?currency=GBP",
                LocalDateTime.now(),
                Status.READY,
                categories));
        urls.add(new Url("https://www.creationwatches.com/products/casio-watches-73/index-1-5d.html?currency=GBP",
                LocalDateTime.now(),
                Status.READY,
                categories));
        urls.add(new Url("https://www.creationwatches.com/products/citizen-74/index-1-5d.html?currency=GBP",
                LocalDateTime.now(),
                Status.READY,
                categories));
        urls.add(new Url("https://www.creationwatches.com/products/orient-watches-252/index-1-5d.html?currency=GBP",
                LocalDateTime.now(),
                Status.READY,
                categories));
        urls.add(new Url("https://www.creationwatches.com/products/hamilton-watches-250/index-1-5d.html?currency=GBP",
                LocalDateTime.now(),
                Status.READY,
                categories));
        urls.add(new Url("https://www.creationwatches.com/products/timex-watches-434/index-1-5d.html?currency=GBP",
                LocalDateTime.now(),
                Status.READY,
                categories));

        Store store = new Store("Creation Watches", "https://www.creationwatches.com", "https://www.complaintsboard.com/img/business/116976/182x300/creation-watches.jpg", urls);

//        mongoStoreRepository.save(store);
        this.mongoStoreService.getMongoStoreRepository().save(store);

        System.out.println("finished");

    }

    @Scheduled(fixedRate = 60000, initialDelay = 1)
    public void parse() {
        List<Store> storeList = this.mongoStoreService.fetchUrlsToScrapeOld();

        System.out.println(storeList);

        System.out.println("Store count:" + storeList.size());

        System.out.println("1st store url count:" + storeList.get(0).getUrls().size());
        System.out.println("2st store url count:" + storeList.get(1).getUrls().size());

//        List<Store> storeList = this.mongoStoreService.fetchUrlsToScrape();
//
//        assertTrue(storeList.size() > 0, "No Stores found!");
//
//        storeList.forEach(store -> {
//            store.getUrls().forEach(url -> {
//                if (url.isReadyToScrape()) {
//                    this.mongoStoreService.updateUrlStatus(store, url, Status.SCRAPING);
//
//                    switch (store.getName()){
//                        case "Creation Watches":
//                            new MongoCreationWatchesScraper(this.mongoItemRepository, store, url).scrapePages();
//                            break;
//                        case "First Class Watches":
//                            new MongoFirstClassWatchesScraper(this.mongoItemRepository, store, url).scrapePages();
//                            break;
//                    }
//
//                    this.mongoStoreService.updateUrlLastTimeChecked(store, url);
//                    this.mongoStoreService.updateUrlStatus(store, url, Status.READY);
//                }
//            });
//        });

//        storeUrl.forEach(store -> {
//            store.getUrls().forEach(url -> {
//                if (url.getLastChecked().isBefore(LocalDateTime.now().minusHours(2)) && url.getStatus().equals(Status.READY)) {
//                    this.mongoStoreService.updateUrlStatus(store, url, martin.dev.pricer.data.model.mongo.model.Status.SCRAPING);
//
//                    MongoScraper CreationWatches = new MongoCreationWatchesScraper(store, url, this.mongoItemRepository);
//                    CreationWatches.scrapePages();
//
//                    this.mongoStoreService.updateUrlLastTimeChecked(store, url);
//                    this.mongoStoreService.updateUrlStatus(store, url, martin.dev.pricer.data.model.mongo.model.Status.READY);
//                }
//            });
//        });


//        try {
//            storeUrlHandler.setStatusScraping(storeUrl);
//            switch (storeUrl.getStore().getName()) {
//                case "H. Samuel":
//                case "Ernest Jones":
//                    Scraper HSamuelScraper = new HSamuelScraper(storeUrl, this.dealProcessor);
//                    HSamuelScraper.scrapePages();
//                    break;
//                case "Superdrug":
//                    Scraper SuperDrugScraper = new SuperDrugScraper(storeUrl, this.dealProcessor);
//                    SuperDrugScraper.scrapePages();
//                    break;
//                case "Argos":
//                    Scraper ArgosScraper = new ArgosScraper(storeUrl, this.dealProcessor);
//                    ArgosScraper.scrapePages();
//                    break;
////                case "All Beauty":
////                    Scraper AllBeautyScraper = new AllBeautyScraper();
////                    AllBeautyScraper.scrapePages(storeUrl);
////                    break;
//                case "AMJ Watches":
//                    Scraper AMJWatchesScraper = new AMJWatchesScraper(storeUrl, this.dealProcessor);
//                    AMJWatchesScraper.scrapePages();
//                    break;
//                case "Debenhams":
//                    Scraper DebenhamsScraper = new DebenhamsScraper(storeUrl, this.dealProcessor);
//                    DebenhamsScraper.scrapePages();
//                    break;
//                case "Creation Watches":
//                    Scraper CreationWatchesScraper = new CreationWatchesScraper(storeUrl, this.dealProcessor);
//                    CreationWatchesScraper.scrapePages();
//                    break;
//                case "Watcho":
//                    Scraper WatchoScraper = new WatchoScraper(storeUrl, this.dealProcessor);
//                    WatchoScraper.scrapePages();
//                    break;
//                case "Watch Shop":
//                    Scraper WatchShopScraper = new WatchShopScraper(storeUrl, this.dealProcessor);
//                    WatchShopScraper.scrapePages();
//                    break;
//                case "First Class Watches":
//                    Scraper FirstClassWatchesScraper = new FirstClassWatchesScraper(storeUrl, this.dealProcessor);
//                    FirstClassWatchesScraper.scrapePages();
//                    break;
//                case "Gold Smiths":
//                    Scraper GoldSmithsScraper = new GoldSmithsScraper(storeUrl, this.dealProcessor);
//                    GoldSmithsScraper.scrapePages();
//                    break;
//                case "Tic Watches":
//                    Scraper TicWatchesScraper = new TicWatchesScraper(storeUrl, this.dealProcessor);
//                    TicWatchesScraper.scrapePages();
//                    break;
//            }
//        } catch (Exception e) {
//            log.error(Arrays.toString(e.getStackTrace()));
//        } finally {
//            storeUrlHandler.setStatusReady(storeUrl);
//            storeUrlHandler.setLastCheckedTimeToNow(storeUrl);
//        }
    }
}
