package martin.dev.pricer.state.scrapers;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.AbstractParser;
import martin.dev.pricer.scraper.ParserException;
import martin.dev.pricer.scraper.ParserValidator;
import martin.dev.pricer.state.*;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Map;

@Slf4j
public class CreationWatchesScraper extends Scraper {

    public CreationWatchesScraper(String name, ScraperParser scraperParser, ScraperState startingScraperState, Map<State, ScraperState> availableScraperStates) {
        super(name, scraperParser, startingScraperState, availableScraperStates);
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
