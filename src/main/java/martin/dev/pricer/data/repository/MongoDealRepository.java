package martin.dev.pricer.data.repository;

import martin.dev.pricer.data.model.Deal;
import martin.dev.pricer.data.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "deals", path = "deals")
public interface MongoDealRepository extends MongoRepository<Deal, String> {

    Deal findByItem(Item item);

    List<Deal> findAllByAvailableExistsOrderByDealFoundDesc();

    Deal findFirstByItem_IdAndAvailable(String item_id, boolean available);
}
