package martin.dev.pricer.profile;

import martin.dev.pricer.data.repository.MongoStoreRepository;
import martin.dev.pricer.data.service.MongoStoreService;
import martin.dev.pricer.scraper.ParseLauncher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainProfile {

    private MongoStoreRepository mongoStoreRepository;


    public MainProfile(MongoStoreRepository mongoStoreRepository) {
        this.mongoStoreRepository = mongoStoreRepository;
    }

    @Bean
    public MongoStoreService getMongoStoreService() {
        return new MongoStoreService(mongoStoreRepository);
    }

    @Bean
    public ParseLauncher getMongoParser() {
        return new ParseLauncher(getMongoStoreService());
    }


}
