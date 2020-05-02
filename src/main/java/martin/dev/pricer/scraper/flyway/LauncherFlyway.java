package martin.dev.pricer.scraper.flyway;

import martin.dev.pricer.flyway.model.Status;
import martin.dev.pricer.flyway.model.Url;
import martin.dev.pricer.flyway.service.StatusServiceFlyway;
import martin.dev.pricer.flyway.service.UrlServiceFlyway;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

public class LauncherFlyway {

    private StatusServiceFlyway statusServiceFlyway;
    private UrlServiceFlyway urlServiceFlyway;
    private ScraperSubject scraperSubject;

    public LauncherFlyway(StatusServiceFlyway statusServiceFlyway, UrlServiceFlyway urlServiceFlyway, ScraperSubject scraperSubject) {
        this.statusServiceFlyway = statusServiceFlyway;
        this.urlServiceFlyway = urlServiceFlyway;
        this.scraperSubject = scraperSubject;
    }

    @Scheduled(fixedRate = 60 * 1000, initialDelay = 5 * 1000)
    public void run() {
        Status statusReady = this.statusServiceFlyway.findStatusByStatus("Ready");
        Status statusProcessing = this.statusServiceFlyway.findStatusByStatus("Processing");

        LocalDateTime timeInPast = LocalDateTime.now().plusHours(2);
        List<Url> urlsToScrape = this.urlServiceFlyway.fetchUrlByStatus(timeInPast, statusReady);
        urlsToScrape.forEach(url -> {
            this.urlServiceFlyway.updateUrlLastCheckedAtAndStatus(url, LocalDateTime.now(), statusProcessing);
            this.scraperSubject.setStoreAndUrl(url);
            this.scraperSubject.notifyAllObservers();
            this.urlServiceFlyway.updateUrlLastCheckedAtAndStatus(url, LocalDateTime.now(), statusReady);
        });
    }
}
