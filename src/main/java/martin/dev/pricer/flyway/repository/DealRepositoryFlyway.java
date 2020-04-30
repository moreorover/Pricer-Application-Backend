package martin.dev.pricer.flyway.repository;

import martin.dev.pricer.flyway.model.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealRepositoryFlyway extends JpaRepository<Deal, Long> {

}
