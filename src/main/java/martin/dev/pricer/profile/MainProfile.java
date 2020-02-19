package martin.dev.pricer.profile;

import martin.dev.pricer.data.fabric.product.DealProcessor;
import martin.dev.pricer.data.fabric.product.ItemPriceProcessor;
import martin.dev.pricer.data.fabric.product.StatisticsProcessor;
import martin.dev.pricer.data.model.mongo.repository.MongoStoreRepository;
import martin.dev.pricer.data.model.mongo.service.MongoStoreService;
import martin.dev.pricer.data.services.product.*;
import martin.dev.pricer.data.services.store.StoreUrlHandler;
import martin.dev.pricer.data.services.store.StoreUrlRepository;
import martin.dev.pricer.scraper.MongoParseLauncher;
import martin.dev.pricer.scraper.ParseLauncher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainProfile {

    private ItemRepository itemRepository;
    private PriceRepository priceRepository;
    private StatisticsRepository statisticsRepository;

    private StoreUrlRepository storeUrlRepository;

    private MongoStoreRepository mongoStoreRepository;


    public MainProfile(ItemRepository itemRepository, PriceRepository priceRepository, StatisticsRepository statisticsRepository, StoreUrlRepository storeUrlRepository, MongoStoreRepository mongoStoreRepository) {
        this.itemRepository = itemRepository;
        this.priceRepository = priceRepository;
        this.statisticsRepository = statisticsRepository;
        this.storeUrlRepository = storeUrlRepository;
        this.mongoStoreRepository = mongoStoreRepository;
    }

    @Bean
    public StoreUrlHandler getStoreUrlService() {
        return new StoreUrlHandler(storeUrlRepository);
    }

    @Bean
    public ItemService getItemService() {
        return new ItemService(itemRepository);
    }

    @Bean
    public PriceService getPriceService() {
        return new PriceService(priceRepository);
    }

    @Bean
    public StatisticsService getStatisticsService() {
        return new StatisticsService(statisticsRepository);
    }

    @Bean
    public ItemPriceProcessor getItemPriceProcessor() {
        return new ItemPriceProcessor(getItemService(), getPriceService(), getStatisticsService());
    }

    @Bean
    public DealProcessor getDealProcessor() {
        return new DealProcessor(getItemService(), getPriceService(), getStatisticsService());
    }

    @Bean
    public StatisticsProcessor getStatisticsProcessor() {
        return new StatisticsProcessor(getItemService(), getPriceService(), getStatisticsService());
    }

    @Bean
    public ParseLauncher getParser() {
        return new ParseLauncher(getStoreUrlService(), getDealProcessor());
    }

    @Bean
    public MongoStoreService getMongoStoreService() { return new MongoStoreService(mongoStoreRepository);}

    @Bean
    public MongoParseLauncher getMongoParser() {
        return new MongoParseLauncher(getMongoStoreService());
    }



}
