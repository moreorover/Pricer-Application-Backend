package martin.dev.pricer.scraper.scrapers;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.*;

import java.util.Map;

@Slf4j
public class DebenhamsScraper extends Scraper {

    public DebenhamsScraper(String name, ScraperParser scraperParser, ScraperState startingScraperState, Map<State, ScraperState> availableScraperStates) {
        super(name, scraperParser, startingScraperState, availableScraperStates);
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
