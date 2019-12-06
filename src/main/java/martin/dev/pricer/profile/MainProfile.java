package martin.dev.pricer.profile;

import martin.dev.pricer.data.fabric.product.DealProcessor;
import martin.dev.pricer.data.fabric.product.ItemPriceProcessor;
import martin.dev.pricer.data.fabric.product.StatisticsProcessor;
import martin.dev.pricer.data.services.product.*;
import martin.dev.pricer.data.services.store.StoreUrlHandler;
import martin.dev.pricer.data.services.store.StoreUrlRepository;
import martin.dev.pricer.scraper.ParseLauncher;
import martin.dev.pricer.scraper.parser.amjwatches.AMJWatchesScraper;
import martin.dev.pricer.scraper.parser.argos.ArgosScraper;
import martin.dev.pricer.scraper.parser.creationwatches.CreationWatchesScraper;
import martin.dev.pricer.scraper.parser.debenhams.DebenhamsScraper;
import martin.dev.pricer.scraper.parser.ernestjones.ErnestJonesScraper;
import martin.dev.pricer.scraper.parser.hsamuel.HSamuelScraper;
import martin.dev.pricer.scraper.parser.inactive.allbeauty.AllBeautyScraper;
import martin.dev.pricer.scraper.parser.superdrug.SuperDrugScraper;
import martin.dev.pricer.scraper.parser.watcho.WatchoScraper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainProfile {

    private ItemRepository itemRepository;
    private PriceRepository priceRepository;
    private StatisticsRepository statisticsRepository;

    private StoreUrlRepository storeUrlRepository;


    public MainProfile(ItemRepository itemRepository, PriceRepository priceRepository, StatisticsRepository statisticsRepository, StoreUrlRepository storeUrlRepository) {
        this.itemRepository = itemRepository;
        this.priceRepository = priceRepository;
        this.statisticsRepository = statisticsRepository;
        this.storeUrlRepository = storeUrlRepository;
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
    public HSamuelScraper getHSamuelScraper() {
        return new HSamuelScraper();
    }

    @Bean
    public ErnestJonesScraper getErnestJonesScraper() {
        return new ErnestJonesScraper();
    }

    @Bean
    public SuperDrugScraper getSuperDrugScraper() {
        return new SuperDrugScraper();
    }

    @Bean
    public ArgosScraper getArgosScraper() {
        return new ArgosScraper();
    }

    @Bean
    public AllBeautyScraper getAllBeautyScraper() {
        return new AllBeautyScraper();
    }

    @Bean
    public AMJWatchesScraper getAMJWatchesScraper() {
        return new AMJWatchesScraper();
    }

    @Bean
    public DebenhamsScraper getDebenhamsScraper() {
        return new DebenhamsScraper();
    }

    @Bean
    public CreationWatchesScraper getCreationWatchesScraper() {
        return new CreationWatchesScraper();
    }

    @Bean
    public WatchoScraper getWatchoScraper() {
        return new WatchoScraper();
    }

    @Bean
    public ParseLauncher getParser() {
        return new ParseLauncher(getStoreUrlService(), getHSamuelScraper(), getErnestJonesScraper(), getSuperDrugScraper(), getArgosScraper(), getAllBeautyScraper(), getAMJWatchesScraper(), getDebenhamsScraper(), getCreationWatchesScraper(), getWatchoScraper());
    }
}
