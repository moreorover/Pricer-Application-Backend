package martin.dev.pricer.flyway.service;

import martin.dev.pricer.flyway.model.Status;
import martin.dev.pricer.flyway.repository.StatusRepositoryFlyway;
import org.springframework.stereotype.Service;

@Service
public class StatusServiceFlyway {

    private StatusRepositoryFlyway statusRepositoryFlyway;

    public StatusServiceFlyway(StatusRepositoryFlyway statusRepositoryFlyway) {
        this.statusRepositoryFlyway = statusRepositoryFlyway;
    }

    public Status findStatusByStatus(String status) {
        return this.statusRepositoryFlyway.findStatusByStatus(status);
    }
}
