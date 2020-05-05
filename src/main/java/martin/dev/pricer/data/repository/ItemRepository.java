package martin.dev.pricer.data.repository;

import martin.dev.pricer.data.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Item findItemByUpc(String upc);
}
