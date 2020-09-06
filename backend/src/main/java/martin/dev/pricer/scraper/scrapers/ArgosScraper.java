package martin.dev.pricer.scraper.scrapers;

import martin.dev.pricer.scraper.*;

import java.util.Map;

public class ArgosScraper extends Scraper {

    public ArgosScraper(String name, ScraperParser scraperParser, ScraperState startingScraperState, Map<State, ScraperState> availableScraperStates) {
        super(name, scraperParser, startingScraperState, availableScraperStates);
    }

    @Override
    public void nextPageUrl() {
        String[] x = this.getCurrentPageUrl().split("opt/page:");
        int pageNumber = ScraperTools.parseIntegerFromString(x[1]) + 1;
        this.setCurrentPageNumber(pageNumber);
        this.setCurrentPageUrl(x[0] + "opt/page:" + pageNumber + "/");
    }
}
