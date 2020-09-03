package martin.dev.pricer.state;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;

import java.io.IOException;

@Slf4j
public class ScraperFetchingHtmlState extends ScraperState {

    @Override
    public void fetchHtml(Scraper scraper) {
        while (true) {
            try {
                log.info("Attempting to fetch Html for:\n" + scraper.getCurrentPageUrl());
                scraper.setPageHtmlDocument(Jsoup.connect(scraper.getCurrentPageUrl()).get());
                break;
            } catch (IOException e) {
                log.error("Catching IO Exception on: " + scraper.getCurrentPageUrl());
            }
        }
        scraper.changeState(State.ParsingHtml);
        scraper.validateResponse();
    }
}
