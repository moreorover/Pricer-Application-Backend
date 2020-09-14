package martin.dev.pricer.scraper.scrapers;

import martin.dev.pricer.scraper.*;

public class ArgosScraper extends Scraper {

    public ArgosScraper(WebClient webClient, DataReader dataReader, Parser parser, DataProcessor dataProcessor, DataWriter dataWriter) {
        super("Argos", webClient, dataReader, parser, dataProcessor, dataWriter);
    }

    @Override
    public void nextPageUrl() {
        String[] x = this.getCurrentPageUrl().split("opt/page:");
        int pageNumber = ScraperTools.parseIntegerFromString(x[1]) + 1;
        this.setCurrentPageNumber(pageNumber);
        this.setCurrentPageUrl(x[0] + "opt/page:" + pageNumber + "/");
    }
}
