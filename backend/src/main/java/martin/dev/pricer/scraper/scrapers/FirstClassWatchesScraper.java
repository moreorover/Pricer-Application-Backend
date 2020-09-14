package martin.dev.pricer.scraper.scrapers;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.*;

@Slf4j
public class FirstClassWatchesScraper extends Scraper {

    public FirstClassWatchesScraper(WebClient webClient, DataReader dataReader, Parser parser, DataProcessor dataProcessor, DataWriter dataWriter, DataSender dataSender) {
        super("First Class Watches", webClient, dataReader, parser, dataProcessor, dataWriter,dataSender);
    }

    @Override
    public void nextPageUrl() {
        String[] x = this.getCurrentPageUrl().split("&page=");
        int pageNumber = ScraperTools.parseIntegerFromString(x[1]) + 1;
        this.setCurrentPageNumber(pageNumber);
        this.setCurrentPageUrl(x[0] + "&page=" + pageNumber);
    }
}
