package martin.dev.pricer.flyway.repository;

import martin.dev.pricer.flyway.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepositoryFlyway extends JpaRepository<Item, Long> {

    Item findItemByUpc(String upc);
}
