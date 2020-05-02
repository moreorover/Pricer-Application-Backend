package martin.dev.pricer.flyway.service;

import martin.dev.pricer.flyway.model.Status;
import martin.dev.pricer.flyway.model.Url;
import martin.dev.pricer.flyway.repository.StatusRepositoryFlyway;
import martin.dev.pricer.flyway.repository.UrlRepositoryFlyway;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
