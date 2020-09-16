package martin.dev.pricer.scraper;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

@Slf4j
@Data
public class JsoupWebClient implements WebClient {

    public void fetchSourceHtml(Scraper scraper) {
        log.info("Attempting to fetch Html for:\n" + scraper.getCurrentPageUrl());
        try {
            Connection connection = Jsoup.connect(scraper.getCurrentPageUrl());
            if (connection.response().statusCode() < 400) {
                Document document = connection.get();
                document.setBaseUri(scraper.getUrl().getStore().getUrl());
                scraper.setPageHtmlDocument(document);
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

    @Override
    public void close() {

    }
}
