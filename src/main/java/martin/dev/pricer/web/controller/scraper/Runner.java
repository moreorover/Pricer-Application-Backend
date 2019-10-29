package martin.dev.pricer.web.controller.scraper;

import martin.dev.pricer.data.request.ParseDto;
import martin.dev.pricer.data.services.store.StoreUrlHandler;
import martin.dev.pricer.scraper.Parser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scrape")
public class Runner {

    private StoreUrlHandler storeUrlHandler;

    public Runner(StoreUrlHandler storeUrlHandler) {
        this.storeUrlHandler = storeUrlHandler;
    }

    @GetMapping
    public ResponseEntity<?> getItems(@RequestBody ParseDto parseDto) {
        Parser parser = new Parser(storeUrlHandler);

        parser.parse(parseDto.getDays(), parseDto.getHours(), parseDto.getMinutes());

        return new ResponseEntity<>(HttpStatus.OK);

    }
}
