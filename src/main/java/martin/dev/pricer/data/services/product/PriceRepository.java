package martin.dev.pricer.data.services.product;

import martin.dev.pricer.data.model.product.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
@Service
public interface PriceRepository extends JpaRepository<Price, Long> {
}
