package martin.dev.pricer.state;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;

import java.io.IOException;

@Slf4j
public class ScraperFetchingHtmlState implements ScraperState {
    @Override
    public void fetchUrl(Scraper scraper) {

    }

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

    @Override
    public void validateResponse(Scraper scraper) {

    }

    @Override
    public void parseResponseToAds(Scraper scraper) {

    }

    @Override
    public void validateAds(Scraper scraper) {

    }

    @Override
    public void parseAdsToItems(Scraper scraper) {

    }

    @Override
    public void validateItems(Scraper scraper) {

    }

    @Override
    public void processItems(Scraper scraper) {

    }

    @Override
    public void writeItems(Scraper scraper) {

    }

    @Override
    public void sendItems(Scraper scraper) {

    }

    @Override
    public void nextPage(Scraper scraper) {

    }
}
