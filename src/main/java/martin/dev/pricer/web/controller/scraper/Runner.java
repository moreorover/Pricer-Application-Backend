package martin.dev.pricer.web.controller.scraper;

import martin.dev.pricer.data.services.product.ItemRepository;
import martin.dev.pricer.data.services.product.PriceRepository;
import martin.dev.pricer.scraper.client.HttpClient;
import martin.dev.pricer.scraper.parser.hsamuel.HSamuelPage;
import org.jsoup.nodes.Document;
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

        Document pageContentInJsoup = HttpClient.readContentInJsoupDocument("https://www.hsamuel.co.uk/webstore/l/watches/recipient%7Chim/?icid=hs-nv-watches-him&Pg=1");
        HSamuelPage hSamuelMain = new HSamuelPage(pageContentInJsoup, itemRepository, priceRepository);
        hSamuelMain.parseListOfAdElements();
        hSamuelMain.parseElementsToItems();

        return new ResponseEntity<>(HttpStatus.OK);

    }
}
