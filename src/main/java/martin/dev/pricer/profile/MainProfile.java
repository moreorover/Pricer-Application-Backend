package martin.dev.pricer.profile;

import martin.dev.pricer.data.fabric.product.ItemPriceProcessor;
import martin.dev.pricer.data.services.product.ItemService;
import martin.dev.pricer.data.services.product.ItemRepository;
import martin.dev.pricer.data.services.product.PriceService;
import martin.dev.pricer.data.services.product.PriceRepository;
import martin.dev.pricer.data.services.store.StoreUrlHandler;
import martin.dev.pricer.data.services.store.StoreUrlRepository;
import martin.dev.pricer.scraper.Parser;
import martin.dev.pricer.scraper.parser.argos.ArgosService;
import martin.dev.pricer.scraper.parser.ernestjones.ErnestJonesParserProcessor;
import martin.dev.pricer.scraper.parser.hsamuel.HSamuelParserProcessor;
import martin.dev.pricer.scraper.parser.superdrug.SuperDrugParserProcessor;
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
    public HSamuelParserProcessor getHSamuelParserProcessor() {
        return new HSamuelParserProcessor(getItemPriceProcessor());
    }

    @Bean
    public ErnestJonesParserProcessor getErnestJonesParserProcessor(){
        return new ErnestJonesParserProcessor(getItemPriceProcessor());
    }

    @Bean
    public SuperDrugParserProcessor getSuperDrugParserProcessor(){
        return new SuperDrugParserProcessor(getItemPriceProcessor());
    }

    @Bean
    public ArgosService getArgosService(){
        return new ArgosService(getItemPriceProcessor());
    }

    @Bean
    public Parser getParser() {
        return new Parser(getStoreUrlHandler(), getHSamuelParserProcessor(), getErnestJonesParserProcessor(), getSuperDrugParserProcessor(), getArgosService());
    }
}
