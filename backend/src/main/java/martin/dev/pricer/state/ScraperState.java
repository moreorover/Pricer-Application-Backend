package martin.dev.pricer.state;

public interface ScraperState {

    void fetchUrl(Scraper scraper);
    void fetchHtml(Scraper scraper);
    void validateResponse(Scraper scraper);
    void parseResponseToAds(Scraper scraper);
    void validateAds(Scraper scraper);
    void parseAdsToItems(Scraper scraper);
    void validateItems(Scraper scraper);
    void processItems(Scraper scraper);
    void writeItems(Scraper scraper);
    void sendItems(Scraper scraper);
    void nextPage(Scraper scraper);
}
