package martin.dev.pricer.scraper.scrapers;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.*;

@Slf4j
public class DebenhamsScraper extends Scraper {

    public DebenhamsScraper(WebClient webClient, DataReader dataReader, Parser parser, DataProcessor dataProcessor, DataWriter dataWriter, DataSender dataSender) {
        super("Debenhams", webClient, dataReader, parser, dataProcessor, dataWriter, dataSender);
    }

    @Override
    public void nextPageUrl() {
        // https://www.debenhams.com/men/accessories/watches?pn=1&?shipToCntry=GB
        String[] x = this.getCurrentPageUrl().split("pn=");
        String[] y = x[1].split("&?sh");
        int pageNumber = ScraperTools.parseIntegerFromString(y[0]) + 1;
        this.setCurrentPageNumber(pageNumber);
        this.setCurrentPageUrl(x[0] + "pn=" + pageNumber + "&?shipToCntry=GB");

    }
}
