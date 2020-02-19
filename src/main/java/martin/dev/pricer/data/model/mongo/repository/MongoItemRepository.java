package martin.dev.pricer.data.model.mongo.repository;

import martin.dev.pricer.data.model.mongo.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoItemRepository extends MongoRepository<Item, String> {

    Item findByUpc(String s);

    Boolean existsByUpc(String upc);
}
