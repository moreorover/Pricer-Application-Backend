package martin.dev.pricer.profile;

import martin.dev.pricer.data.fabric.product.ItemPriceProcessor;
import martin.dev.pricer.data.services.product.ItemRepository;
import martin.dev.pricer.data.services.product.ItemService;
import martin.dev.pricer.data.services.product.PriceRepository;
import martin.dev.pricer.data.services.product.PriceService;
import martin.dev.pricer.data.services.store.StoreUrlHandler;
import martin.dev.pricer.data.services.store.StoreUrlRepository;
import martin.dev.pricer.scraper.ParseLauncher;
import martin.dev.pricer.scraper.parser.debenhams.DebenhamsScraper;
import martin.dev.pricer.scraper.parser.inactive.allbeauty.AllBeautyScraper;
import martin.dev.pricer.scraper.parser.amjwatches.AMJWatchesScraper;
import martin.dev.pricer.scraper.parser.argos.ArgosScraper;
import martin.dev.pricer.scraper.parser.ernestjones.ErnestJonesScraper;
import martin.dev.pricer.scraper.parser.hsamuel.HSamuelScraper;
import martin.dev.pricer.scraper.parser.superdrug.SuperDrugScraper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainProfile {

    private ItemRepository itemRepository;
    private PriceRepository priceRepository;

    private StoreUrlRepository storeUrlRepository;


    public MainProfile(ItemRepository itemRepository, PriceRepository priceRepository, StoreUrlRepository storeUrlRepository) {
        this.itemRepository = itemRepository;
        this.priceRepository = priceRepository;
        this.storeUrlRepository = storeUrlRepository;
    }

    @Bean
    public StoreUrlHandler getStoreUrlHandler() {
        return new StoreUrlHandler(storeUrlRepository);
    }

    @Bean
    public ItemService getItemHandler() {
        return new ItemService(itemRepository);
    }

    @Bean
    public PriceService getPriceHandler() {
        return new PriceService(priceRepository);
    }

    @Bean
    public ItemPriceProcessor getItemPriceProcessor() {
        return new ItemPriceProcessor(getItemHandler(), getPriceHandler());
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
    public SuperDrugScraper getSuperDrugScraper() { return new SuperDrugScraper(); }

    @Bean
    public ArgosScraper getArgosScraper() {
        return new ArgosScraper();
    }

    @Bean
    public AllBeautyScraper getAllBeautyScraper() { return new AllBeautyScraper(); }

    @Bean
    public AMJWatchesScraper getAMJWatchesScraper() { return new AMJWatchesScraper(); }

    @Bean
    public DebenhamsScraper getDebenhamsScraper() { return new DebenhamsScraper(); }

    @Bean
    public ParseLauncher getParser() {
        return new ParseLauncher(getStoreUrlHandler(), getHSamuelScraper(), getErnestJonesScraper(), getSuperDrugScraper(), getArgosScraper(), getAllBeautyScraper(), getAMJWatchesScraper(), getDebenhamsScraper());
    }
}
