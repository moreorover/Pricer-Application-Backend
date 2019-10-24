package martin.dev.pricer.web.controller.product;

import martin.dev.pricer.data.model.product.Item;
import martin.dev.pricer.data.services.product.ItemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {

    private ItemRepository itemRepository;

    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping
    public ResponseEntity<List<Item>> getItems() {

        List<Item> items = itemRepository.findAll();
        return new ResponseEntity<>(items, HttpStatus.OK);

    }
}
