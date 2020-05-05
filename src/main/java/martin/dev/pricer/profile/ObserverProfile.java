package martin.dev.pricer.profile;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("prodremote")
@Configuration
public class ObserverProfile {

//    private StoreRepository storeRepository;
//    private ItemRepository itemRepository;
//    private DealRepository dealRepository;
//    private ParserErrorRepository parserErrorRepository;
//
//    public ObserverProfile(StoreRepository storeRepository, ItemRepository itemRepository, DealRepository dealRepository, ParserErrorRepository parserErrorRepository) {
//        this.storeRepository = storeRepository;
//        this.itemRepository = itemRepository;
//        this.dealRepository = dealRepository;
//        this.parserErrorRepository = parserErrorRepository;
//    }
//
//    @Bean
//    public ItemService getMongoItemService() {
//        return new ItemService(itemRepository, dealRepository);
//    }
//
//    @Bean
//    public StoreService getMongoStoreService() {
//        return new StoreService(storeRepository);
//    }
//
//    @Bean
//    public ParserErrorService getParserErrorService() {
//        return new ParserErrorService(parserErrorRepository);
//    }
//
//    @Bean
//    public ScraperSubject scraperSubject(){
//        return new ScraperSubject();
//    }
//
//    @Bean
//    public ParserHandler AMJWatchesParser(){
//        return new ParserHandlerMongo(new AMJWatchesParser(), getParserErrorService());
//    }
//
//    @Bean
//    public ParserHandler ArgosParser(){
//        return new ParserHandlerMongo(new ArgosParser(), getParserErrorService());
//    }
//
//    @Bean
//    public ParserHandler CreationWatchesParser(){
//        return new ParserHandlerMongo(new CreationWatchesParser(), getParserErrorService());
//    }
//
//    @Bean
//    public ParserHandler DebenhamsParser(){
//        return new ParserHandlerMongo(new DebenhamsParser(), getParserErrorService());
//    }
//
//    @Bean
//    public ParserHandler ErnestJonesParser(){
//        return new ParserHandlerMongo(new ErnestJonesParser(), getParserErrorService());
//    }
//
//    @Bean
//    public ParserHandler FirstClassWatchesParser(){
//        return new ParserHandlerMongo(new FirstClassWatchesParser(), getParserErrorService());
//    }
//
//    @Bean
//    public ParserHandler GoldSmithsParser(){
//        return new ParserHandlerMongo(new GoldSmithsParser(), getParserErrorService());
//    }
//
//    @Bean
//    public ParserHandler HSamuelParser(){
//        return new ParserHandlerMongo(new HSamuelParser(), getParserErrorService());
//    }
//
//    @Bean
//    public ParserHandler SuperDrugParser(){
//        return new ParserHandlerMongo(new SuperDrugParser(), getParserErrorService());
//    }
//
//    @Bean
//    public ParserHandler TicWatchesParser(){
//        return new ParserHandlerMongo(new TicWatchesParser(), getParserErrorService());
//    }
//
//    @Bean
//    public ParserHandler WatchoParser(){
//        return new ParserHandlerMongo(new WatchoParser(), getParserErrorService());
//    }
//
//    @Bean
//    public ParserHandler WatchShopParser(){
//        return new ParserHandlerMongo(new WatchShopParser(), getParserErrorService());
//    }
//
//    @Bean
//    public ParserHandler SimpkinsJewellersParser(){
//        return new ParserHandlerMongo(new SimpkinsJewellersParser(), getParserErrorService());
//    }
//
//    @Bean
//    public ScraperSubject getSubject() {
//        ScraperSubject subject = new ScraperSubject();
//        new Scraper(subject, AMJWatchesParser(), getMongoItemService(), 1);
//        new Scraper(subject, ArgosParser(), getMongoItemService(), 1);
//        new Scraper(subject, CreationWatchesParser(), getMongoItemService(), 1);
//        new Scraper(subject, DebenhamsParser(), getMongoItemService(), 1);
//        new Scraper(subject, ErnestJonesParser(), getMongoItemService(), 1);
//        new Scraper(subject, FirstClassWatchesParser(), getMongoItemService(), 1);
//        new Scraper(subject, GoldSmithsParser(), getMongoItemService(), 1);
//        new Scraper(subject, HSamuelParser(), getMongoItemService(), 1);
//        new Scraper(subject, SuperDrugParser(), getMongoItemService(), 0);
//        new Scraper(subject, TicWatchesParser(), getMongoItemService(), 1);
//        new Scraper(subject, WatchoParser(), getMongoItemService(), 1);
//        new Scraper(subject, WatchShopParser(), getMongoItemService(), 1);
//        new Scraper(subject, SimpkinsJewellersParser(), getMongoItemService(), 1);
//        return subject;
//    }
//
//    @Bean
//    public Launcher runner() {
//        return new Launcher(getMongoStoreService(), getSubject());
//    }
}
