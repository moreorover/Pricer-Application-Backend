package martin.dev.pricer.data.services.product;

import martin.dev.pricer.data.model.product.Item;
import martin.dev.pricer.data.model.product.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Service
public interface PriceRepository extends JpaRepository<Price, Long> {

    Price findFirstByItemOrderByFoundAtDesc(Item item);

    List<Price> findAllByItem(Item item);

    Price findFirstByItemOrderByPriceDesc(Item item);

    Price findFirstByItemOrderByPriceAsc(Item item);

    List<Price> findAllByDeltaIsLessThan(Double delta);
}
