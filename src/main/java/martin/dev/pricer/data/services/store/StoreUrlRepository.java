package martin.dev.pricer.data.services.store;

import martin.dev.pricer.data.model.store.Status;
import martin.dev.pricer.data.model.store.Store;
import martin.dev.pricer.data.model.store.StoreUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Service
public interface StoreUrlRepository extends JpaRepository<StoreUrl, Long> {

    List<StoreUrl> findAllByStore(Store store);

    StoreUrl findFirstByLastCheckedBeforeAndStatusOrderByLastCheckedAsc(LocalDateTime lastChecked, Status status);
}
