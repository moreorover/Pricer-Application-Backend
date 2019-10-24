package martin.dev.pricer.web.controller.product;

import martin.dev.pricer.data.model.product.Price;
import martin.dev.pricer.data.services.product.PriceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/price")
public class PriceController {

    private PriceRepository priceRepository;

    public PriceController(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @GetMapping
    public ResponseEntity<List<Price>> getItems() {

        List<Price> prices = priceRepository.findAll();
        return new ResponseEntity<>(prices, HttpStatus.OK);

    }
}
