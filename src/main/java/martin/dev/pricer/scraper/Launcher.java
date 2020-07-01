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

    @Scheduled(fixedRate = 30 * 1000, initialDelay = 2 * 1000)
    public void postDealsToDiscord() {
        long amjwatches = 727982475971788862L;
        long argos = 727981213611983011L;
        long creationwatches = 727982262829973585L;
        long debenhams = 727982663864156170L;
        long ernestjones = 727982375224475678L;
        long firstclasswatches = 727982302621204571L;
        long goldsmiths = 727982530359197726L;
        long hsamuel = 727982352751657112L;
        long simpkins = 727982761343844372L;
        long superdrug = 727982629961334866L;
        long ticwatches = 727982558582931538L;
        long watchshop = 727982449325375569L;
        long watcho = 727982735947464787L;
        long s = 727983020333858906L;
        long m = 727983491391946792L;
        long l = 727983528071004260L;
        long xl = 727983602658443345L;
        long seiko = 727990059831132190L;


        List<Deal> dealsToPost = this.dealService.fetchDealsToPostToDiscord();

        dealsToPost.forEach(deal -> {
            if (deal.getItem().getLastDelta() <= -20 && deal.getItem().getLastDelta() > -30) {
                discordService.sendMessage(deal, s);
            }
            if (deal.getItem().getLastDelta() <= -30 && deal.getItem().getLastDelta() > -40) {
                discordService.sendMessage(deal, m);
            }
            if (deal.getItem().getLastDelta() <= -40 && deal.getItem().getLastDelta() > -50) {
                discordService.sendMessage(deal, l);
            }
            if (deal.getItem().getLastDelta() <= -50 && deal.getItem().getLastDelta() > -100) {
                discordService.sendMessage(deal, xl);
            }

            switch (deal.getItem().getUrlObject().getStore().getName()) {
                case "AMJ Watches":
                    discordService.sendMessage(deal, amjwatches);
                    break;
                case "Argos":
                    discordService.sendMessage(deal, argos);
                    break;
                case "Creation Watches":
                    discordService.sendMessage(deal, creationwatches);
                    break;
                case "Debenhams":
                    discordService.sendMessage(deal, debenhams);
                    break;
                case "Ernest Jones":
                    discordService.sendMessage(deal, ernestjones);
                    break;
                case "First Class Watches":
                    discordService.sendMessage(deal, firstclasswatches);
                    break;
                case "Gold Smiths":
                    discordService.sendMessage(deal, goldsmiths);
                    break;
                case "H. Samuel":
                    discordService.sendMessage(deal, hsamuel);
                    break;
                case "Simpkins Jewellers":
                    discordService.sendMessage(deal, simpkins);
                    break;
                case "Superdrug":
                    discordService.sendMessage(deal, superdrug);
                    break;
                case "Tic Watches":
                    discordService.sendMessage(deal, ticwatches);
                    break;
                case "Watch Shop":
                    discordService.sendMessage(deal, watchshop);
                    break;
                case "Watcho":
                    discordService.sendMessage(deal, watcho);
                    break;
            }

            if (deal.getItem().getName().toLowerCase().contains("seiko")) {
                discordService.sendMessage(deal, seiko);
            }

            dealService.updateDealToPosted(deal);
        });
    }


}
