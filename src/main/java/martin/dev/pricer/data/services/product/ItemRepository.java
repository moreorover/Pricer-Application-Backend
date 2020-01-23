package martin.dev.pricer.data.services.product;

import martin.dev.pricer.data.model.product.Item;
import martin.dev.pricer.data.model.product.Price;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
@Service
public interface ItemRepository extends JpaRepository<Item, Long> {

    Item findItemByUpc(String upc);

    List<Item> findAllByStatistics_Deal(boolean statistics_deal);

    Item findFirstByStatisticsNull();

    Page<Item> findAllByStatistics_DealOrderByStatistics_lastFoundDesc(boolean statistics_deal, Pageable pageable);

    Page<Item> findAllByStatistics_LastDeltaIsLessThanOrderByStatistics_lastFoundDesc(double statistics_lastDelta, Pageable pageable);

    Page<Item> findAllByStatistics_LastDeltaIsLessThanAndStatistics_DealOrderByStatistics_lastFoundDesc(double statistics_lastDelta, boolean statistics_deal, Pageable pageable);
}
