package martin.dev.pricer.web.controller.product;

import martin.dev.pricer.data.model.product.Item;
import martin.dev.pricer.data.services.product.DealRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deals")
@CrossOrigin
public class DealsController {

    private DealRepository dealRepository;

    public DealsController(DealRepository dealRepository) {
        this.dealRepository = dealRepository;
    }

    @GetMapping
    public ResponseEntity<List<Item>> getDeals(@RequestParam(required = false, defaultValue = "1") int pageNum,
                                               @RequestParam(required = false, defaultValue = "50") int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        List<Item> items = dealRepository.findAllByStatistics_DealOrderByStatistics_lastFoundDesc(true, pageable);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("less")
    public ResponseEntity<List<Item>> getDealsLess(@RequestParam(required = false, defaultValue = "1") int pageNum,
                                               @RequestParam(required = false, defaultValue = "50") int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        List<Item> items = dealRepository.findAllByStatistics_LastDeltaIsLessThanOrderByStatistics_lastFoundDesc(-9.0, pageable);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }
}
