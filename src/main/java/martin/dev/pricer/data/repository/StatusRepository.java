package martin.dev.pricer.data.repository;

import martin.dev.pricer.data.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {

    Status findStatusByStatus(String status);
}
