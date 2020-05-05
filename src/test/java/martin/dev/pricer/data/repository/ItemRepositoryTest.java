package martin.dev.pricer.data.repository;

import martin.dev.pricer.data.model.Item;
import martin.dev.pricer.data.model.Price;
import martin.dev.pricer.data.model.Url;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles({"test"})
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UrlRepository urlRepository;

    @Test
    public void saveItem(){

        Optional<Url> url = urlRepository.findById(3L);

        Item item = new Item();
        item.setUpc("some upc");
        item.setName("some name");
        item.setUrl("some url");
        item.setPrice(12.4);
        item.setDelta(-50.1);
        item.setDelta(-50.1);
        item.setFoundTime(LocalDateTime.now());
        item.setFoundWhere("where found");
        item.setUrlObject(url.get());
        itemRepository.save(item);

        List<Item> items = itemRepository.findAll();

        assertEquals(2, items.size());
    }

    @Test
    public void savePriceToItem(){

        Item item = itemRepository.findItemByUpc("some upc");
        assertNotNull(item);

        Price price = new Price();
        price.setDelta(-41.1);
        price.setPrice(14.10);
        price.setFoundTime(LocalDateTime.now());

        item.newPrice(price);
        itemRepository.save(item);

        Item items = itemRepository.findItemByUpc("some upc");
        assertEquals(1, items.getPrices().size());
        assertEquals(14.10, items.getMaxPrice());
    }

}