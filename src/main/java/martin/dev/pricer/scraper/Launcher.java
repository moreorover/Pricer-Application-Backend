package martin.dev.pricer.scraper;

import martin.dev.pricer.data.model.Deal;
import martin.dev.pricer.data.model.Status;
import martin.dev.pricer.data.model.Url;
import martin.dev.pricer.data.service.DealService;
import martin.dev.pricer.data.service.StatusService;
import martin.dev.pricer.data.service.UrlService;
import martin.dev.pricer.discord.DiscordService;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

public class Launcher {

    private StatusService statusService;
    private UrlService urlService;
    private DealService dealService;
    private ScraperSubject scraperSubject;

    private DiscordService discordService;

    public Launcher(StatusService statusService, UrlService urlService, DealService dealService, ScraperSubject scraperSubject, DiscordService discordService) {
        this.statusService = statusService;
        this.urlService = urlService;
        this.dealService = dealService;
        this.scraperSubject = scraperSubject;
        this.discordService = discordService;
    }

    @Scheduled(fixedRate = 60 * 1000, initialDelay = 5 * 1000)
    public void scrape() {
        Status statusReady = this.statusService.findStatusByStatus("Ready");
        Status statusProcessing = this.statusService.findStatusByStatus("Processing");
        Status statusDisabled = this.statusService.findStatusByStatus("Disabled");

        LocalDateTime timeInPast = LocalDateTime.now().minusHours(2);
        List<Url> urlsToScrape = this.urlService.fetchUrlByStatusAndCheckedAtBefore(statusReady, timeInPast);
        urlsToScrape.stream()
                .filter(Url::isReadyToScrape)
                .forEach(url -> {
                    this.urlService.updateUrlLastCheckedAtAndStatus(url, LocalDateTime.now(), statusProcessing);
                    this.scraperSubject.setStoreAndUrl(url);
                    this.scraperSubject.notifyAllObservers();
                    this.urlService.updateUrlLastCheckedAtAndStatus(url, LocalDateTime.now(), statusReady);
                });
    }

//    @Scheduled(fixedRate = 30 * 1000, initialDelay = 6 * 1000)
    public void postDealsToDiscord() {
        List<Deal> dealsToPost = this.dealService.fetchDealsToPostToDiscord();

        dealsToPost.forEach(deal -> {
            if (deal.getItem().getImg() != null && !deal.getItem().getImg().equals("")){
                discordService.sendEmbeddedWithImage(deal);
            } else {
                discordService.sendEmbeddedWithoutImage(deal);
            }
            dealService.updateDealToPosted(deal);
        });
    }


}
