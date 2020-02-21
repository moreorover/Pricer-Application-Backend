package martin.dev.pricer.data.model.mongo.repository;

import martin.dev.pricer.data.model.mongo.model.Deal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoDealRepository extends MongoRepository<Deal, String> {
}
