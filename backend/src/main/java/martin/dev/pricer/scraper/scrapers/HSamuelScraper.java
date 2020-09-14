package martin.dev.pricer.scraper.scrapers;

import martin.dev.pricer.scraper.*;

public class HSamuelScraper extends Scraper {

    public HSamuelScraper(WebClient webClient, DataReader dataReader, Parser parser, DataProcessor dataProcessor, DataWriter dataWriter) {
        super("H. Samuel", webClient, dataReader, parser, dataProcessor, dataWriter);
    }

    @Override
    public void nextPageUrl() {
        String[] x = this.getCurrentPageUrl().split("Pg=");
        int pageNumber = ScraperTools.parseIntegerFromString(x[1]) + 1;
        this.setCurrentPageNumber(pageNumber);
        this.setCurrentPageUrl(x[0] + "Pg=" + pageNumber);
    }
}
