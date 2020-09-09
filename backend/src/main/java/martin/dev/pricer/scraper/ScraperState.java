package martin.dev.pricer.scraper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ScraperState {

    void fetchUrl(Scraper scraper) {
        log.error("Calling an empty fetchUrl method!");
    }

    void fetchHtml(Scraper scraper) {
        log.error("Calling an empty fetchHtml method!");
    }

    void validateResponse(Scraper scraper) {
        log.error("Calling an empty validateResponse method!");
    }

    void parseResponseToAds(Scraper scraper) {
        log.error("Calling an empty parseResponseToAds method!");
    }

    void validateAds(Scraper scraper) {
        log.error("Calling an empty validateAds method!");
    }

    void parseAdsToItems(Scraper scraper) {
        log.error("Calling an empty parseAdsToItems method!");
    }

    void validateItems(Scraper scraper) {
        log.error("Calling an empty validateItems method!");
    }

    void processItems(Scraper scraper) {
        log.error("Calling an empty processItems method!");
    }

    void writeItems(Scraper scraper) {
        log.error("Calling an empty writeItems method!");
    }

    void sendItems(Scraper scraper) {
        log.error("Calling an empty sendItems method!");
    }

    void nextPage(Scraper scraper) {
        log.error("Calling an empty nextPage method!");
    }

}
