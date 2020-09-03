package martin.dev.pricer.web.controller;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.model.Deal;
import martin.dev.pricer.data.model.Item;
import martin.dev.pricer.data.model.dto.parent.DealDtoParent;
import martin.dev.pricer.data.service.DealService;
import martin.dev.pricer.data.service.ItemService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/api/deal")
@CrossOrigin
@Slf4j
public class DealController {

    @Autowired
    private DealService dealService;

    @Autowired
    private ItemService itemService;

    private final ModelMapper modelMapper = new ModelMapper();

    //    private DealRepository dealRepository;
//
//    public DealController(DealRepository dealRepository) {
//        this.dealRepository = dealRepository;
//    }
//
    @GetMapping
    public ResponseEntity<List<DealDtoParent>> getDeals(@RequestParam(required = false, defaultValue = "1") int page,
                                                        @RequestParam(required = false, defaultValue = "50") int size) {

        List<Deal> deals = this.dealService.fetchAllDealsOderedByFounded(page, size);
        Type listType = new TypeToken<List<DealDtoParent>>() {
        }.getType();
        List<DealDtoParent> dealDtos = modelMapper.map(deals, listType);
        return new ResponseEntity<>(dealDtos, HttpStatus.OK);
    }

    @GetMapping("/item")
    public ResponseEntity<List<Item>> getItems(@RequestParam(required = false, defaultValue = "1") int page,
                                               @RequestParam(required = false, defaultValue = "50") int size) {

        List<Item> deals = this.itemService.fetchItemsByDealLessThanZero(page, size);
//        Type listType = new TypeToken<List<DealDto>>() {}.getType();
//        List<DealDto> dealDtos = modelMapper.map(deals, listType);
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
//    }¬
//
    @GetMapping("/{dealId}")
    public ResponseEntity<DealDtoParent> findDealById(@PathVariable Long dealId) {
        Deal deal = this.dealService.findDealById(dealId);
        DealDtoParent dealDtoParent = modelMapper.map(deal, DealDtoParent.class);
        return new ResponseEntity<>(dealDtoParent, HttpStatus.OK);
    }
}
