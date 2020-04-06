package martin.dev.pricer.profile;

import martin.dev.pricer.data.repository.DealRepository;
import martin.dev.pricer.data.repository.ItemRepository;
import martin.dev.pricer.data.repository.StoreRepository;
import martin.dev.pricer.data.service.ItemService;
import martin.dev.pricer.data.service.StoreService;
import martin.dev.pricer.scraper.ScraperSubject;
import martin.dev.pricer.scraper.parser.*;
import martin.dev.pricer.scraper.Launcher;
import martin.dev.pricer.scraper.ParserHandler;
import martin.dev.pricer.scraper.Scraper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("prodremote")
@Configuration
public class ObserverProfile {

    private StoreRepository storeRepository;
    private ItemRepository itemRepository;
    private DealRepository dealRepository;

    public ObserverProfile(StoreRepository storeRepository, ItemRepository itemRepository, DealRepository dealRepository) {
        this.storeRepository = storeRepository;
        this.itemRepository = itemRepository;
        this.dealRepository = dealRepository;
    }

    @Bean
    public ItemService getMongoItemService() {
        return new ItemService(itemRepository, dealRepository);
    }

    @Bean
    public StoreService getMongoStoreService() {
        return new StoreService(storeRepository);
    }

    @Bean
    public ScraperSubject scraperSubject(){
        return new ScraperSubject();
    }

    @Bean
    public ParserHandler AMJWatchesParser(){
        return new ParserHandler(new AMJWatchesParser());
    }

    @Bean
    public ParserHandler ArgosParser(){
        return new ParserHandler(new ArgosParser());
    }

    @Bean
    public ParserHandler CreationWatchesParser(){
        return new ParserHandler(new CreationWatchesParser());
    }

    @Bean
    public ParserHandler DebenhamsParser(){
        return new ParserHandler(new DebenhamsParser());
    }

    @Bean
    public ParserHandler ErnestJonesParser(){
        return new ParserHandler(new ErnestJonesParser());
    }

    @Bean
    public ParserHandler FirstClassWatchesParser(){
        return new ParserHandler(new FirstClassWatchesParser());
    }

    @Bean
    public ParserHandler GoldSmithsParser(){
        return new ParserHandler(new GoldSmithsParser());
    }

    @Bean
    public ParserHandler HSamuelParser(){
        return new ParserHandler(new HSamuelParser());
    }

    @Bean
    public ParserHandler SuperDrugParser(){
        return new ParserHandler(new SuperDrugParser());
    }

    @Bean
    public ParserHandler TicWatchesParser(){
        return new ParserHandler(new TicWatchesParser());
    }

    @Bean
    public ParserHandler WatchoParser(){
        return new ParserHandler(new WatchoParser());
    }

    @Bean
    public ParserHandler WatchShopParser(){
        return new ParserHandler(new WatchShopParser());
    }

    @Bean
    public ParserHandler SimpkinsJewellersParser(){
        return new ParserHandler(new SimpkinsJewellersParser());
    }

    @Bean
    public ScraperSubject getSubject() {
        ScraperSubject subject = new ScraperSubject();
        new Scraper(subject, AMJWatchesParser(), getMongoItemService(), 1);
        new Scraper(subject, ArgosParser(), getMongoItemService(), 1);
        new Scraper(subject, CreationWatchesParser(), getMongoItemService(), 1);
        new Scraper(subject, DebenhamsParser(), getMongoItemService(), 1);
        new Scraper(subject, ErnestJonesParser(), getMongoItemService(), 1);
        new Scraper(subject, FirstClassWatchesParser(), getMongoItemService(), 1);
        new Scraper(subject, GoldSmithsParser(), getMongoItemService(), 1);
        new Scraper(subject, HSamuelParser(), getMongoItemService(), 1);
        new Scraper(subject, SuperDrugParser(), getMongoItemService(), 0);
        new Scraper(subject, TicWatchesParser(), getMongoItemService(), 1);
        new Scraper(subject, WatchoParser(), getMongoItemService(), 1);
        new Scraper(subject, WatchShopParser(), getMongoItemService(), 1);
        new Scraper(subject, SimpkinsJewellersParser(), getMongoItemService(), 1);
        return subject;
    }

    @Bean
    public Launcher runner() {
        return new Launcher(getMongoStoreService(), getSubject());
    }
}