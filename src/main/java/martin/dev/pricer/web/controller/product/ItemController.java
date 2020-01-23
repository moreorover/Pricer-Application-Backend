package martin.dev.pricer.web.controller.product;

import martin.dev.pricer.data.model.product.Item;
import martin.dev.pricer.data.services.product.ItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
@CrossOrigin
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

    @GetMapping("/deals")
    public ResponseEntity<List<Item>> getDeals() {
        List<Item> items = itemRepository.findAllByStatistics_Deal(true);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/deals/paged")
    public ResponseEntity<Page<Item>> getDealsPaged(@RequestParam(required = false, defaultValue = "1") int pageNum,
                                                    @RequestParam(required = false, defaultValue = "250") int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<Item> items = itemRepository.findAllByStatistics_DealOrderByStatistics_lastFoundDesc(true, pageable);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/deals/less")
    public ResponseEntity<Page<Item>> getDealsLess(@RequestParam(required = false, defaultValue = "1") int pageNum,
                                                   @RequestParam(required = false, defaultValue = "250") int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<Item> items = itemRepository.findAllByStatistics_LastDeltaIsLessThanAndStatistics_DealOrderByStatistics_lastFoundDesc(-9.0, true, pageable);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }
}
