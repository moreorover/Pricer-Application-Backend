package martin.dev.pricer.profile;

import martin.dev.pricer.data.repository.DealRepository;
import martin.dev.pricer.data.repository.ItemRepository;
import martin.dev.pricer.data.repository.StoreRepository;
import martin.dev.pricer.data.service.ItemService;
import martin.dev.pricer.data.service.StoreService;
import martin.dev.pricer.obs.strategy.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.security.auth.Subject;

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
    public ParserHandler HSamuelParser(){
        return new ParserHandler(new HSamuelParser());
    }

    @Bean
    public HSamuelScraper HSamuelScraper() {
        return new HSamuelScraper(scraperSubject(), HSamuelParser(), getMongoItemService2(), 1);
    }

    @Bean
    public ScraperSubject getSubject() {
        ScraperSubject subject = new ScraperSubject();
        new HSamuelScraper(subject, HSamuelParser(), getMongoItemService2(), 1);
        return subject;
    }

    @Bean
    public Launcher runner() {
        return new Launcher(getMongoStoreService2(), getSubject());
    }


}
