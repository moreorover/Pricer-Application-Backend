package martin.dev.pricer.state;

import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;
import java.util.Map;

public class Begin {

    private ScraperReadingState scraperReadingState;
    private ScraperFetchingHtmlState scraperFetchingHtmlState;
    private ScraperParsingHtmlState scraperParsingHtmlState;
    private ScraperProcessingState scraperProcessingState;

    public Begin(ScraperReadingState scraperReadingState, ScraperFetchingHtmlState scraperFetchingHtmlState, ScraperParsingHtmlState scraperParsingHtmlState, ScraperProcessingState scraperProcessingState) {
        this.scraperReadingState = scraperReadingState;
        this.scraperFetchingHtmlState = scraperFetchingHtmlState;
        this.scraperParsingHtmlState = scraperParsingHtmlState;
        this.scraperProcessingState = scraperProcessingState;
    }

    @Scheduled(fixedRate = 60 * 1000, initialDelay = 5 * 1000)
    public void begin() {
        System.out.println("Beginning to scrape");
        Map<State, ScraperState> availableStates = new HashMap<>();
        availableStates.put(State.ReadingDatabase, scraperReadingState);
        availableStates.put(State.FetchingHtml, scraperFetchingHtmlState);
        availableStates.put(State.ParsingHtml, scraperParsingHtmlState);
        availableStates.put(State.ProcessingAds, scraperProcessingState);
        Scraper hSamuelScraper = new HSamuelScraper(new HSamuelParser(), this.scraperReadingState, availableStates);
        hSamuelScraper.fetchUrl();
    }
}
