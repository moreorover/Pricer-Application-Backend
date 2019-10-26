package martin.dev.pricer.web.controller.scraper;

import martin.dev.pricer.data.services.product.ItemRepository;
import martin.dev.pricer.data.services.product.PriceRepository;
import martin.dev.pricer.scraper.parser.hsamuel.Parser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scrape")
public class Runner {

    private ItemRepository itemRepository;
    private PriceRepository priceRepository;

    public Runner(ItemRepository itemRepository, PriceRepository priceRepository) {
        this.itemRepository = itemRepository;
        this.priceRepository = priceRepository;
    }

    @GetMapping
    public ResponseEntity<?> getItems() {

        Parser parser = new Parser(itemRepository, priceRepository);

        parser.parse();

        return new ResponseEntity<>(HttpStatus.OK);

    }
}
