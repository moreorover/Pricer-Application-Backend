package martin.dev.pricer.flyway.repository;

import martin.dev.pricer.flyway.model.Status;
import martin.dev.pricer.flyway.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UrlRepositoryFlyway extends JpaRepository<Url, Long> {

    List<Url> findAllByCheckedAtBeforeOrCheckedAtIsNullAndStatusOrderByCheckedAtAsc(LocalDateTime checkedAt, Status status);
}
