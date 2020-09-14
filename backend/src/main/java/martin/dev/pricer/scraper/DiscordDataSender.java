package martin.dev.pricer.scraper;

import martin.dev.pricer.data.model.Deal;
import martin.dev.pricer.data.service.DealService;
import martin.dev.pricer.discord.DiscordService;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public class DiscordDataSender extends DataSender {

    private final DealService dealService;
    private final DiscordService discordService;

    @Value("${price.discord.posting.on}")
    private boolean DISCORD_POSTING_ON;

    public DiscordDataSender(DealService dealService, DiscordService discordService) {
        this.dealService = dealService;
        this.discordService = discordService;
    }

    @Override
    public void send() {
        if (DISCORD_POSTING_ON) {
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
                    this.discordService.sendMessage(deal, s);
                }
                if (deal.getItem().getLastDelta() <= -30 && deal.getItem().getLastDelta() > -40) {
                    this.discordService.sendMessage(deal, m);
                }
                if (deal.getItem().getLastDelta() <= -40 && deal.getItem().getLastDelta() > -50) {
                    this.discordService.sendMessage(deal, l);
                }
                if (deal.getItem().getLastDelta() <= -50 && deal.getItem().getLastDelta() > -100) {
                    this.discordService.sendMessage(deal, xl);
                }

                switch (deal.getItem().getUrlObject().getStore().getName()) {
                    case "AMJ Watches":
                        this.discordService.sendMessage(deal, amjwatches);
                        break;
                    case "Argos":
                        this.discordService.sendMessage(deal, argos);
                        break;
                    case "Creation Watches":
                        this.discordService.sendMessage(deal, creationwatches);
                        break;
                    case "Debenhams":
                        this.discordService.sendMessage(deal, debenhams);
                        break;
                    case "Ernest Jones":
                        this.discordService.sendMessage(deal, ernestjones);
                        break;
                    case "First Class Watches":
                        this.discordService.sendMessage(deal, firstclasswatches);
                        break;
                    case "Gold Smiths":
                        this.discordService.sendMessage(deal, goldsmiths);
                        break;
                    case "H. Samuel":
                        this.discordService.sendMessage(deal, hsamuel);
                        break;
                    case "Simpkins Jewellers":
                        this.discordService.sendMessage(deal, simpkins);
                        break;
                    case "Superdrug":
                        this.discordService.sendMessage(deal, superdrug);
                        break;
                    case "Tic Watches":
                        this.discordService.sendMessage(deal, ticwatches);
                        break;
                    case "Watch Shop":
                        this.discordService.sendMessage(deal, watchshop);
                        break;
                    case "Watcho":
                        this.discordService.sendMessage(deal, watcho);
                        break;
                }

                if (deal.getItem().getName().toLowerCase().contains("seiko") && deal.getItem().getDelta() > 10) {
                    this.discordService.sendMessage(deal, seiko);
                }

                this.dealService.updateDealToPosted(deal);
            });
        }
    }
}
