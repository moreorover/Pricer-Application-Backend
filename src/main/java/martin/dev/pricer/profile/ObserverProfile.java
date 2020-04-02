package martin.dev.pricer.profile;

import martin.dev.pricer.data.repository.DealRepository;
import martin.dev.pricer.data.repository.ItemRepository;
import martin.dev.pricer.data.repository.StoreRepository;
import martin.dev.pricer.data.service.ItemService;
import martin.dev.pricer.data.service.StoreService;
import martin.dev.pricer.scraper.parser.*;
import martin.dev.pricer.scraper.Launcher;
import martin.dev.pricer.scraper.ParserHandler;
import martin.dev.pricer.scraper.Scraper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public ItemService getMongoItemService2() {
        return new ItemService(itemRepository, dealRepository);
    }

    @Bean
    public StoreService getMongoStoreService2() {
        return new StoreService(storeRepository);
    }

    @Bean
    public ScraperSubject scraperSubject(){
        return new ScraperSubject();
    }

    @Bean
    public ParserHandler AMHWatchesParser(){
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
    public ScraperSubject getSubject() {
        ScraperSubject subject = new ScraperSubject();
        new Scraper(subject, AMHWatchesParser(), getMongoItemService2(), 1);
        new Scraper(subject, ArgosParser(), getMongoItemService2(), 1);
        new Scraper(subject, CreationWatchesParser(), getMongoItemService2(), 1);
        new Scraper(subject, DebenhamsParser(), getMongoItemService2(), 1);
        new Scraper(subject, ErnestJonesParser(), getMongoItemService2(), 1);
        new Scraper(subject, FirstClassWatchesParser(), getMongoItemService2(), 1);
        new Scraper(subject, GoldSmithsParser(), getMongoItemService2(), 1);
        new Scraper(subject, HSamuelParser(), getMongoItemService2(), 1);
        new Scraper(subject, SuperDrugParser(), getMongoItemService2(), 0);
        new Scraper(subject, TicWatchesParser(), getMongoItemService2(), 1);
        new Scraper(subject, WatchoParser(), getMongoItemService2(), 1);
        new Scraper(subject, WatchShopParser(), getMongoItemService2(), 1);
        return subject;
    }

    @Bean
    public Launcher runner() {
        return new Launcher(getMongoStoreService2(), getSubject());
    }
}
