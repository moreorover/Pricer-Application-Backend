package martin.dev.pricer.flyway.service;

import martin.dev.pricer.flyway.repository.DealRepositoryFlyway;
import org.springframework.stereotype.Service;

@Service
public class DealServiceFlyway {

    private DealRepositoryFlyway dealRepositoryFlyway;

    public DealServiceFlyway(DealRepositoryFlyway dealRepositoryFlyway) {
        this.dealRepositoryFlyway = dealRepositoryFlyway;
    }
}
