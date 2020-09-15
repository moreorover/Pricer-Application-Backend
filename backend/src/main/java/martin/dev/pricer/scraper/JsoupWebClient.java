package martin.dev.pricer.scraper;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

@EqualsAndHashCode(callSuper = true)
@Slf4j
@Data
public class JsoupWebClient extends WebClient {

    @Override
    public void fetchSourceHtml(Scraper scraper) {
        log.info("Attempting to fetch Html for:\n" + scraper.getCurrentPageUrl());
        try {
            Connection connection = Jsoup.connect(scraper.getCurrentPageUrl());
            if (connection.response().statusCode() < 400) {
                scraper.setPageHtmlDocument(connection.get());
                scraper.parseResponseToAds();
            } else {
                throw new RuntimeException("Response code above: " + connection.response().statusCode());
            }
        } catch (IOException e) {
            log.error("Catching IO Exception");
            log.error(e.getMessage());
        } catch (RuntimeException e) {
            log.error("Catching Response Code exception.");
            log.error(e.getMessage());
        }


    }
}
