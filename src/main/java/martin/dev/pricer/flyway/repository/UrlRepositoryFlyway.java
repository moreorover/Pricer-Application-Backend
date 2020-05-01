package martin.dev.pricer.flyway.repository;

import martin.dev.pricer.flyway.model.Status;
import martin.dev.pricer.flyway.model.Url;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UrlRepositoryFlyway extends JpaRepository<Url, Long> {

    List<Url> findAllByCheckedAtBeforeOrCheckedAtIsNullAndStatusOrderByCheckedAtAsc(LocalDateTime checkedAt, Status status);

    @NotNull Optional<Url> findById(@NotNull Long id);

}
