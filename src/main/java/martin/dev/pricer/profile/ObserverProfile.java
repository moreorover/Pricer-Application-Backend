package martin.dev.pricer.profile;

import martin.dev.pricer.data.repository.StoreRepository;
import martin.dev.pricer.data.service.StoreService;
import martin.dev.pricer.obs.ErnestJonesObserver;
import martin.dev.pricer.obs.HSamuelObserver;
import martin.dev.pricer.obs.Runner;
import martin.dev.pricer.obs.Subject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObserverProfile {

    private StoreRepository storeRepository;

    public ObserverProfile(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Bean
    public StoreService getMongoStoreService2() {
        return new StoreService(storeRepository);
    }

    @Bean
    public Subject getSubject() {
        Subject subject = new Subject();
        new HSamuelObserver(subject);
        new ErnestJonesObserver(subject);
        return subject;
    }

    @Bean
    public Runner runner() {
        return new Runner(getMongoStoreService2(), getSubject());
    }


}
