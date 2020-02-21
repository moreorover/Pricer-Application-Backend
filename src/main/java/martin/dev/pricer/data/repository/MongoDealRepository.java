package martin.dev.pricer.data.repository;

import martin.dev.pricer.data.model.Deal;
import martin.dev.pricer.data.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MongoDealRepository extends MongoRepository<Deal, String> {

    Deal findByItem(Item item);

    Deal findByItemAndAvailable(Item item, boolean available);
}
