package martin.dev.pricer.data.repository;

import martin.dev.pricer.data.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {

    Item findByUpc(String s);

    Boolean existsByUpc(String upc);
}
