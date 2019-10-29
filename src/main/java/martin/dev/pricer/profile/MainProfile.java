package martin.dev.pricer.profile;

import martin.dev.pricer.data.fabric.product.ItemPriceProcessor;
import martin.dev.pricer.data.services.product.ItemRepository;
import martin.dev.pricer.data.services.product.PriceRepository;
import martin.dev.pricer.data.services.store.StoreUrlHandler;
import martin.dev.pricer.data.services.store.StoreUrlRepository;
import martin.dev.pricer.scraper.Parser;
import martin.dev.pricer.scraper.parser.hsamuel.HSamuelParserProcessor;
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
    public StoreUrlHandler getStoreUrlHandler(){
        return new StoreUrlHandler(storeUrlRepository);
    }

    @Bean
    public ItemPriceProcessor getItemPriceProcessor(){
        return new ItemPriceProcessor(itemRepository, priceRepository);
    }

    @Bean
    public HSamuelParserProcessor getHSamuelParserProcessor(){
        return new HSamuelParserProcessor(getItemPriceProcessor());
    }

    @Bean
    public Parser getParser(){
        return new Parser(getStoreUrlHandler(), getHSamuelParserProcessor());
    }
}
