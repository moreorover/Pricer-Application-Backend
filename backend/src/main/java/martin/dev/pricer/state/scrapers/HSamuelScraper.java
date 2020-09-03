package martin.dev.pricer.state.scrapers;

import martin.dev.pricer.state.*;

import java.util.Map;

public class HSamuelScraper extends Scraper {

    public HSamuelScraper(String name, ScraperParser scraperParser, ScraperState startingScraperState, Map<State, ScraperState> availableScraperStates) {
        super(name, scraperParser, startingScraperState, availableScraperStates);
    }

    @Override
    public void nextPageUrl() {
        String[] x = this.getCurrentPageUrl().split("Pg=");
        int pageNumber = ScraperTools.parseIntegerFromString(x[1]) + 1;
        this.setCurrentPageNumber(pageNumber);
        this.setCurrentPageUrl(x[0] + "Pg=" + pageNumber);
    }
}
