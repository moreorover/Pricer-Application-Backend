package martin.dev.pricer.data.repository;

import martin.dev.pricer.data.model.Store;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoStoreRepository extends MongoRepository<Store, String> {
}
