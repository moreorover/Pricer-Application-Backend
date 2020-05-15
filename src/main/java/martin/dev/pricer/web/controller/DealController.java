package martin.dev.pricer.web.controller;

import martin.dev.pricer.data.model.Deal;
import martin.dev.pricer.data.model.Item;
import martin.dev.pricer.data.service.DealService;
import martin.dev.pricer.data.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deals")
@CrossOrigin
public class DealController {

    @Autowired
    private DealService dealService;

    @Autowired
    private ItemService itemService;

//    private DealRepository dealRepository;
//
//    public DealController(DealRepository dealRepository) {
//        this.dealRepository = dealRepository;
//    }
//
    @GetMapping
    public ResponseEntity<List<Item>> getDeals(@RequestParam(required = false, defaultValue = "1") int page,
                                               @RequestParam(required = false, defaultValue = "50") int size) {

        List<Item> deals = this.itemService.fetchItemsByDealLessThanZero(page, size);
        return new ResponseEntity<>(deals, HttpStatus.OK);
    }
//
//    @GetMapping("/store")
//    public ResponseEntity<List<Deal>> getDealsByStore(@RequestParam(required = true) String storeId,
//                                                      @RequestParam(required = false, defaultValue = "1") int page,
//                                                      @RequestParam(required = false, defaultValue = "50") int size) {
//        Pageable pageable = PageRequest.of(page - 1, size);
//        List<Deal> deals = this.dealRepository.findByAvailableAndStore_idOrderByDealFoundDesc(true, storeId, pageable);
//        return new ResponseEntity<>(deals, HttpStatus.OK);
//    }
//
//    @GetMapping(path = "/find")
//    public ResponseEntity<Deal> findDeal(@RequestParam(required = true) String itemId) {
//        Deal deal = this.dealRepository.findFirstByItem_IdAndAvailableOrderByDealFoundDesc(itemId, true);
//        return new ResponseEntity<>(deal, HttpStatus.OK);
//    }
//
//    @GetMapping(path = "/deal")
//    public ResponseEntity<Deal> findDealById(@RequestParam(required = true) String dealId) {
//        @NotNull Optional<Deal> deal = this.dealRepository.findById(dealId);
//        return new ResponseEntity<>(deal.get(), HttpStatus.OK);
//    }
}
