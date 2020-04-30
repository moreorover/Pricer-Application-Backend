package martin.dev.pricer.flyway.repository;

import martin.dev.pricer.flyway.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepositoryFlyway extends JpaRepository<Store, Long> {
}
