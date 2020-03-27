package martin.dev.pricer.profile;

import martin.dev.pricer.data.repository.StoreRepository;
import martin.dev.pricer.data.service.StoreService;
import martin.dev.pricer.obs.Runner;
import martin.dev.pricer.scraper.ParseLauncher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainProfile {

    private StoreRepository storeRepository;


    public MainProfile(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Bean
    public StoreService getMongoStoreService() {
        return new StoreService(storeRepository);
    }

    @Bean
    public ParseLauncher getMongoParser() {
        return new ParseLauncher(getMongoStoreService());
    }

    @Bean
    public Runner runner() {
        return new Runner(getMongoStoreService());
    }


}
