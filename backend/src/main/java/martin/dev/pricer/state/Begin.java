package martin.dev.pricer.state;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Slf4j
public class Begin {

    private final List<Scraper> scrapers;

    @Value("${price.scraping.on}")
    private boolean SCRAPING_ON;

    public Begin(List<Scraper> scrapers) {
        this.scrapers = scrapers;
    }

    @Scheduled(fixedRate = 40 * 1000, initialDelay = 5 * 1000)
    public void begin() {
        if (SCRAPING_ON) {
            log.info("Notifying each scraper to fetch URL");
            scrapers.forEach(Scraper::fetchUrl);
        }

    }
}
