package martin.dev.pricer.data.repository;

import martin.dev.pricer.data.model.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {

    Item findByUpc(String s);

    Boolean existsByUpc(String upc);

    List<Item> findAllBy(Pageable pageable);
}
