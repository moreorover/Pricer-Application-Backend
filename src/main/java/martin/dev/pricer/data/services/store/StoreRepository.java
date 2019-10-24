package martin.dev.pricer.data.services.store;

import martin.dev.pricer.data.model.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
@Service
public interface StoreRepository extends JpaRepository<Store, Long> {
}
