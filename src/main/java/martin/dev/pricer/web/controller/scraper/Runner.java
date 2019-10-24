package martin.dev.pricer.web.controller.scraper;

import martin.dev.pricer.scraper.client.HttpClient;
import martin.dev.pricer.scraper.parser.hsamuel.HSamuelMain;
import org.jsoup.nodes.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scrape")
public class Runner {

    @GetMapping
    public ResponseEntity<?> getItems() {

        Document pageContentInJsoup = HttpClient.readContentInJsoupDocument("https://www.hsamuel.co.uk/webstore/l/watches/recipient%7Chim/?icid=hs-nv-watches-him&Pg=1");
        HSamuelMain hSamuelMain = new HSamuelMain(pageContentInJsoup);
        hSamuelMain.parseListOfAdElements();
        hSamuelMain.parseElementsToItems();

        return new ResponseEntity<>(HttpStatus.OK);

    }
}
