package martin.dev.pricer.profile;

import martin.dev.pricer.data.services.store.StoreUrlHandler;
import martin.dev.pricer.data.services.store.StoreUrlRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainProfile {

    private StoreUrlRepository storeUrlRepository;

    public MainProfile(StoreUrlRepository storeUrlRepository) {
        this.storeUrlRepository = storeUrlRepository;
    }

    @Bean
    public StoreUrlHandler getStoreUrlHandler(){
        return new StoreUrlHandler(storeUrlRepository);
    }
}
