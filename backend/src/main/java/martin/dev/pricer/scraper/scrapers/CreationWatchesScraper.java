package martin.dev.pricer.scraper.scrapers;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.*;

@Slf4j
public class CreationWatchesScraper extends Scraper {

    public CreationWatchesScraper(WebClient webClient, DataReader dataReader, Parser parser, DataProcessor dataProcessor, DataWriter dataWriter, DataSender dataSender) {
        super("Creation Watches", webClient, dataReader, parser, dataProcessor, dataWriter, dataSender);
    }

    @Override
    public void nextPageUrl() {
        String[] x = this.getCurrentPageUrl().split("/index-");
        String[] y = x[1].split("-5d");
        int pageNumber = ScraperTools.parseIntegerFromString(y[0]) + 1;
        this.setCurrentPageNumber(pageNumber);
        this.setCurrentPageUrl(x[0] + "/index-" + pageNumber + "-5d.html?currency=GBP");

    }
}
