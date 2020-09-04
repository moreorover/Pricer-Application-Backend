package martin.dev.pricer.state;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

@Slf4j
public class ScraperFetchingHtmlState extends ScraperState {

    @Override
    public void fetchHtml(Scraper scraper) {
        while (true) {
            try {
                log.info("Attempting to fetch Html for:\n" + scraper.getCurrentPageUrl());
                Document d = Jsoup
                        .connect(scraper.getCurrentPageUrl())
//                        .proxy("127.0.0.1", 8888)
                        .get();

                scraper.setPageHtmlDocument(d);
                break;
            } catch (IOException e) {
                log.error("Catching IO Exception on: " + scraper.getCurrentPageUrl());
            }
        }
        scraper.changeState(State.ParsingHtml);
        scraper.validateResponse();
    }
}
