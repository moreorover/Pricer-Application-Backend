package martin.dev.pricer.data.services.product;

import martin.dev.pricer.data.model.product.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface DealRepository extends PagingAndSortingRepository<Item, Long> {

    List<Item> findAllByStatistics_DealOrderByStatistics_lastFoundDesc(boolean statistics_deal, Pageable pageable);
}
