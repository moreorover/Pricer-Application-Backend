package martin.dev.pricer.data.model.mongo.repository;

import martin.dev.pricer.data.model.mongo.model.Store;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoStoreRepository extends MongoRepository<Store, String> {
}
