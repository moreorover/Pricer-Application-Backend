package martin.dev.pricer.flyway.repository;

import martin.dev.pricer.flyway.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepositoryFlyway extends JpaRepository<Status, Long> {

    Status findStatusByStatus(String status);
}
