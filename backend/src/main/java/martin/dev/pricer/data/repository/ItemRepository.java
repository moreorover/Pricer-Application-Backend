package martin.dev.pricer.data.repository;

import martin.dev.pricer.data.model.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Item findItemByUpc(String upc);

    List<Item> findAllByDeltaIsLessThanOrderByFoundTimeDesc(double delta, Pageable pageable);
}
