package martin.dev.pricer.flyway.repository;

import martin.dev.pricer.flyway.model.Store;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles({"test"})
class StoreRepositoryFlywayTest {

    @Autowired
    private StoreRepositoryFlyway storeRepositoryFlyway;

    @Test
    public void checkIfDbIsPopulated() {
        List<Store> stores = storeRepositoryFlyway.findAll();
        assertNotEquals(stores.size(), 0);
    }

}