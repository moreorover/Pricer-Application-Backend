package martin.dev.pricer.web.controller;

import martin.dev.pricer.data.model.Deal;
import martin.dev.pricer.data.repository.DealRepository;
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

    private DealRepository dealRepository;

    public DealController(DealRepository dealRepository) {
        this.dealRepository = dealRepository;
    }

    @GetMapping
    public ResponseEntity<List<Deal>> getDeals(@RequestParam(required = false, defaultValue = "1") int page,
                                               @RequestParam(required = false, defaultValue = "50") int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        List<Deal> deals = this.dealRepository.findByOrderByDealFoundDesc(pageable);
        return new ResponseEntity<>(deals, HttpStatus.OK);
    }
}
