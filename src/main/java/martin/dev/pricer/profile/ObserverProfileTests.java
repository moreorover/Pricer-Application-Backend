package martin.dev.pricer.profile;

import martin.dev.pricer.data.repository.DealRepository;
import martin.dev.pricer.data.repository.ItemRepository;
import martin.dev.pricer.data.repository.StoreRepository;
import martin.dev.pricer.data.service.ItemService;
import martin.dev.pricer.data.service.StoreService;
import martin.dev.pricer.scraper.Launcher;
import martin.dev.pricer.scraper.ParserHandler;
import martin.dev.pricer.scraper.Scraper;
import martin.dev.pricer.scraper.ScraperSubject;
import martin.dev.pricer.scraper.parser.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("testscollection")
@Configuration
public class ObserverProfileTests {

    private StoreRepository storeRepository;
    private ItemRepository itemRepository;
    private DealRepository dealRepository;

    public ObserverProfileTests(StoreRepository storeRepository, ItemRepository itemRepository, DealRepository dealRepository) {
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
    public ParserHandler getParser(){
        return new ParserHandler(new SimpkinsJewellersParser());
    }

    @Bean
    public ScraperSubject registerListeners() {
        ScraperSubject subject = new ScraperSubject();
        new Scraper(subject, getParser(), getMongoItemService(), parserErrorService, 1);
        return subject;
    }

    @Bean
    public Launcher runner() {
        return new Launcher(getMongoStoreService(), registerListeners());
    }
}
