package martin.dev.pricer.flyway.repository;

import martin.dev.pricer.flyway.model.Status;
import martin.dev.pricer.flyway.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface UrlRepositoryFlyway extends JpaRepository<Url, Long> {

    List<Url> findAllByCheckedAtBeforeOrCheckedAtIsNullAndStatusOrderByCheckedAtAsc(LocalDateTime checkedAt, Status status);
}
