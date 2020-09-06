package martin.dev.pricer.scraper.scrapers;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.*;

import java.util.Map;

@Slf4j
public class TicWatchesScraper extends Scraper {

    public TicWatchesScraper(String name, ScraperParser scraperParser, ScraperState startingScraperState, Map<State, ScraperState> availableScraperStates) {
        super(name, scraperParser, startingScraperState, availableScraperStates);
    }

    @Override
    public void nextPageUrl() {
        // https://www.ticwatches.co.uk/womens-watches-c2?page=1
        String[] x = this.getCurrentPageUrl().split("page=");
        int pageNumber = ScraperTools.parseIntegerFromString(x[1]) + 1;
        this.setCurrentPageNumber(pageNumber);
        this.setCurrentPageUrl(x[0] + "page=" + pageNumber);
    }
}
