package martin.dev.pricer.data.repository;

import martin.dev.pricer.data.model.Store;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles({"test"})
class StoreRepositoryTest {

    @Autowired
    private StoreRepository storeRepository;

    @Test
    public void checkIfDbIsPopulated() {
        List<Store> stores = storeRepository.findAll();
        assertNotEquals(stores.size(), 0);
    }

}