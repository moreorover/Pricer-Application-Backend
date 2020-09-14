package martin.dev.pricer.scraper.scrapers;

import martin.dev.pricer.scraper.*;

public class WatchShopScraper extends Scraper {

    public WatchShopScraper(WebClient webClient, DataReader dataReader, Parser parser, DataProcessor dataProcessor, DataWriter dataWriter, DataSender dataSender) {
        super("Watch Shop", webClient, dataReader, parser, dataProcessor, dataWriter, dataSender);
    }

    @Override
    public void nextPageUrl() {
        String[] x = this.getCurrentPageUrl().split("page=");
        int pageNumber = ScraperTools.parseIntegerFromString(x[1]) + 1;
        this.setCurrentPageNumber(pageNumber);
        this.setCurrentPageUrl(x[0] + "page=" + pageNumber);
    }
}
