package martin.dev.pricer.data.repository;

import martin.dev.pricer.data.model.Item;
import martin.dev.pricer.data.service.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles({"test"})
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void checkPageable() {
        Pageable pageable = PageRequest.of(400 - 1, 100);
        List<Item> items = this.itemRepository.findAllBy(pageable);

        assertNotNull(items);
    }

}