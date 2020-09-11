package martin.dev.pricer.scraper;

import lombok.Data;
import martin.dev.pricer.data.model.Url;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public abstract class Scraper {

    private String name;

    private WebClient webClient;
    private DataReader dataReader;
    private Parser parser;
    private DataProcessor dataProcessor;
    private DataWriter dataWriter;

    private ScraperParser scraperParser;
    private ScraperState scraperState;
    private Map<State, ScraperState> availableScraperStates;

    private Url url;
    private Document pageHtmlDocument;
    private Elements ads;
    private List<ParsedItemDto> items = new ArrayList<>();
    private String currentPageUrl;
    private int currentPageNumber;

    public Scraper(String name, ScraperParser scraperParser, ScraperState startingScraperState, Map<State, ScraperState> availableScraperStates) {
        this.name = name;
        this.scraperParser = scraperParser;
        this.scraperState = startingScraperState;
        this.availableScraperStates = availableScraperStates;
    }

    public void fetchUrl() {
        this.scraperState.fetchUrl(this);
    }

    public void fetchHtml() {
        this.scraperState.fetchHtml(this);
    }

    public void validateResponse() {
        this.scraperState.validateResponse(this);
    }

    public void parseResponseToAds() {
        this.scraperState.parseResponseToAds(this);
    }

    public void validateAds() {
        this.scraperState.validateAds(this);
    }

    public void parseAdsToItems() {
        this.scraperState.parseAdsToItems(this);
    }

    public void validateItems() {
        this.scraperState.validateItems(this);
    }

    public void processItems() {
        this.scraperState.processItems(this);
    }

    public void writeItems() {
        this.scraperState.writeItems(this);
    }

    public void sendItems() {
        this.scraperState.sendItems(this);
    }

    public void nextPage() {
        this.scraperState.nextPage(this);
    }

    public abstract void nextPageUrl();

    public void changeState(State state) {
        this.scraperState = this.availableScraperStates.get(state);
    }
}
