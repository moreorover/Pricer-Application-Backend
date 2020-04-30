package martin.dev.pricer.flyway.repository;

import martin.dev.pricer.flyway.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepositoryFlyway extends JpaRepository<Store, Long> {
}
