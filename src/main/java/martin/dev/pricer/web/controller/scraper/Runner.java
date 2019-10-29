package martin.dev.pricer.web.controller.scraper;

import martin.dev.pricer.data.model.dto.request.ParseDto;
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

    private Parser parser;

    public Runner(Parser parser) {
        this.parser = parser;
    }

    @GetMapping
    public ResponseEntity<?> getItems(@RequestBody ParseDto parseDto) {

        parser.parse(parseDto.getDays(), parseDto.getHours(), parseDto.getMinutes());

        return new ResponseEntity<>(HttpStatus.OK);

    }
}
