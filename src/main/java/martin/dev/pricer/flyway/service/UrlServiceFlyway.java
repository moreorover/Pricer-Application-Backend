package martin.dev.pricer.flyway.service;

import martin.dev.pricer.flyway.model.Status;
import martin.dev.pricer.flyway.model.Url;
import martin.dev.pricer.flyway.repository.UrlRepositoryFlyway;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UrlServiceFlyway {

    private UrlRepositoryFlyway urlRepositoryFlyway;

    public UrlServiceFlyway(UrlRepositoryFlyway urlRepositoryFlyway) {
        this.urlRepositoryFlyway = urlRepositoryFlyway;
    }

    // TODO test this as it returns incorrect dataset
    public List<Url> fetchUrlByStatus(LocalDateTime checkedAt, Status status) {
        return this.urlRepositoryFlyway.findAllByCheckedAtBeforeOrCheckedAtIsNullAndStatusOrderByCheckedAtAsc(checkedAt, status);
    }

    public void updateUrlLastCheckedAtAndStatus(Url url, LocalDateTime checkedAt, Status status) {
        url.setCheckedAt(checkedAt);
        url.setStatus(status);
        this.urlRepositoryFlyway.save(url);
    }
}
