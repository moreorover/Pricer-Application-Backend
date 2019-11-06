package martin.dev.pricer.data.services.product;

import martin.dev.pricer.data.model.product.Item;
import martin.dev.pricer.data.model.product.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
@Service
public interface StatisticsRepository extends JpaRepository<Statistics, Long> {

    Statistics findFirstByItem(Item item);
}
