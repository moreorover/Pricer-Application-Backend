package martin.dev.pricer.scraper;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

@EqualsAndHashCode(callSuper = true)
@Slf4j
@Data
public class JsoupWebClient extends WebClient {

    @Override
    public void fetchSourceHtml(Scraper scraper) {
        log.info("Attempting to fetch Html for:\n" + scraper.getCurrentPageUrl());
        try {
            Document d = Jsoup
                    .connect(scraper.getCurrentPageUrl())
//                    .proxy("127.0.0.1", 8888)
                    .get();
            scraper.setPageHtmlDocument(d);
            scraper.parseResponseToAds();
        } catch (IOException e) {
            log.error(e.getMessage());
        }


    }
}
