package martin.dev.pricer.state.scrapers;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.state.*;

import java.util.Map;

@Slf4j
public class SuperDrugScraper extends Scraper {

    public SuperDrugScraper(String name, ScraperParser scraperParser, ScraperState startingScraperState, Map<State, ScraperState> availableScraperStates) {
        super(name, scraperParser, startingScraperState, availableScraperStates);
    }

    @Override
    public void nextPageUrl() {
        // https://www.superdrug.com/Fragrance/Perfume-For-Women/c/fragranceforher?q=%3AbestBiz%3AinStockFlag%3Atrue&page=0&resultsForPage=60&sort=bestBiz
        String[] x = this.getCurrentPageUrl().split("&page=");
        String[] y = x[1].split("&resultsFor");
        int pageNumber = ScraperTools.parseIntegerFromString(y[0]) + 1;
        this.setCurrentPageNumber(pageNumber);
        this.setCurrentPageUrl(x[0] + "&page=" + pageNumber + "&resultsForPage=60&sort=bestBiz");
    }
}
