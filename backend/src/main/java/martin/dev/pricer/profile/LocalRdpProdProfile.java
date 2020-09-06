package martin.dev.pricer.profile;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.repository.*;
import martin.dev.pricer.data.service.*;
import martin.dev.pricer.discord.DiscordService;
import martin.dev.pricer.state.*;
import martin.dev.pricer.state.scrapers.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Profile("local-rdp-prod")
@Configuration
@Slf4j
public class LocalRdpProdProfile {

    private final StatusRepository statusRepository;
    private final UrlRepository urlRepository;
    private final ItemRepository itemRepository;
    private final ParserErrorRepository parserErrorRepository;
    private final DealRepository dealRepository;

    @Value("${discord.api.key}")
    private String DiscordApiKey;

    @Value("${price.scraping.on}")
    private boolean SCRAPING_ON;

    public LocalRdpProdProfile(StatusRepository statusRepository, UrlRepository urlRepository, ItemRepository itemRepository, ParserErrorRepository parserErrorRepository, DealRepository dealRepository) {
        this.statusRepository = statusRepository;
        this.urlRepository = urlRepository;
        this.itemRepository = itemRepository;
        this.parserErrorRepository = parserErrorRepository;
        this.dealRepository = dealRepository;
    }

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(2);
        return threadPoolTaskScheduler;
    }

    @Bean
    public ItemService getSqlItemService() {
        return new ItemService(itemRepository);
    }

    @Bean
    public StatusService getSqlStatusService() {
        return new StatusService(statusRepository);
    }

    @Bean
    public UrlService getSqlUrlService() {
        return new UrlService(urlRepository);
    }

    @Bean
    public ParserErrorService getParserErrorService() {
        return new ParserErrorService(parserErrorRepository);
    }

    @Bean
    public DealService getDealService() {
        return new DealService(dealRepository);
    }

    @Bean
    public DiscordService discordBot() {
        try {
            JDA jda = JDABuilder.createDefault(DiscordApiKey).build();
            return new DiscordService(jda);
        } catch (LoginException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Bean
    public ScraperReadingState scraperReadingState() {
        return new ScraperReadingState(this.getSqlStatusService(), this.getSqlUrlService());
    }

    @Bean
    public ScraperFetchingHtmlSeleniumState scraperFetchingHtmlSeleniumState() {
        return new ScraperFetchingHtmlSeleniumState();
    }

    @Bean
    public ScraperFetchingHtmlState scraperFetchingHtmlState() {
        return new ScraperFetchingHtmlState();
    }

    @Bean
    public ScraperParsingHtmlState scraperParsingHtmlState() {
        return new ScraperParsingHtmlState(this.getSqlStatusService(), this.getSqlUrlService());
    }

    @Bean
    public ScraperProcessingState scraperProcessingState() {
        return new ScraperProcessingState(this.getSqlItemService());
    }

    @Bean
    public Map<State, ScraperState> singleAdScraperStateFactory() {
        Map<State, ScraperState> availableStates = new HashMap<>();
        availableStates.put(State.ReadingDatabase, scraperReadingState());
        availableStates.put(State.FetchingHtml, scraperFetchingHtmlState());
        availableStates.put(State.ParsingHtml, scraperParsingHtmlState());
        availableStates.put(State.ProcessingAds, scraperProcessingState());
        return availableStates;
    }

    @Bean
    public Map<State, ScraperState> singleAdScraperAndJsStateFactory() {
        Map<State, ScraperState> availableStates = new HashMap<>();
        availableStates.put(State.ReadingDatabase, scraperReadingState());
        availableStates.put(State.FetchingHtml, scraperFetchingHtmlSeleniumState());
        availableStates.put(State.ParsingHtml, scraperParsingHtmlState());
        availableStates.put(State.ProcessingAds, scraperProcessingState());
        return availableStates;
    }

    @Bean
    public martin.dev.pricer.state.Scraper HSamuelScraper() {
        return new HSamuelScraper("H. Samuel", new martin.dev.pricer.state.scrapers.HSamuelParser(), singleAdScraperStateFactory().get(State.ReadingDatabase), singleAdScraperStateFactory());
    }

    @Bean
    public martin.dev.pricer.state.Scraper CreationWatchesScraper() {
        return new CreationWatchesScraper("Creation Watches", new martin.dev.pricer.state.scrapers.CreationWatchesParser(), singleAdScraperStateFactory().get(State.ReadingDatabase), singleAdScraperStateFactory());
    }

    @Bean
    public martin.dev.pricer.state.Scraper FirstClassWatchesScraper() {
        return new FirstClassWatchesScraper("First Class Watches", new martin.dev.pricer.state.scrapers.FirstClassWatchesParser(), singleAdScraperStateFactory().get(State.ReadingDatabase), singleAdScraperStateFactory());
    }

    @Bean
    public martin.dev.pricer.state.Scraper ErnestJonesScraper() {
        return new ErnestJonesScraper("Ernest Jones", new martin.dev.pricer.state.scrapers.ErnestJonesParser(), singleAdScraperStateFactory().get(State.ReadingDatabase), singleAdScraperStateFactory());
    }

    @Bean
    public martin.dev.pricer.state.Scraper WatchShopScraper() {
        return new WatchShopScraper("Watch Shop", new martin.dev.pricer.state.scrapers.WatchShopParser(), singleAdScraperStateFactory().get(State.ReadingDatabase), singleAdScraperStateFactory());
    }

    @Bean
    public martin.dev.pricer.state.Scraper GoldSmithsScraper() {
        return new GoldSmithsScraper("Gold Smiths", new martin.dev.pricer.state.scrapers.GoldSmithsParser(), singleAdScraperStateFactory().get(State.ReadingDatabase), singleAdScraperStateFactory());
    }

    @Bean
    public martin.dev.pricer.state.Scraper TicWatchesScraper() {
        return new TicWatchesScraper("Tic Watches", new martin.dev.pricer.state.scrapers.TicWatchesParser(), singleAdScraperStateFactory().get(State.ReadingDatabase), singleAdScraperStateFactory());
    }

    @Bean
    public martin.dev.pricer.state.Scraper WatchoScraper() {
        return new WatchShopScraper("Watcho", new martin.dev.pricer.state.scrapers.WatchoParser(), singleAdScraperStateFactory().get(State.ReadingDatabase), singleAdScraperStateFactory());
    }

    @Bean
    public martin.dev.pricer.state.Scraper SimpkinsJewellersScraper() {
        return new SimpkinsJewellersScraper("Simpkins Jewellers", new martin.dev.pricer.state.scrapers.SimpkinsJewellersParser(), singleAdScraperStateFactory().get(State.ReadingDatabase), singleAdScraperStateFactory());
    }

    @Bean
    public martin.dev.pricer.state.Scraper ArgosScraper() {
        return new ArgosScraper("Argos", new martin.dev.pricer.state.scrapers.ArgosParser(), singleAdScraperStateFactory().get(State.ReadingDatabase), singleAdScraperStateFactory());
    }

    @Bean
    public martin.dev.pricer.state.Scraper SuperDrugScraper() {
        return new SuperDrugScraper("Superdrug", new martin.dev.pricer.state.scrapers.SuperDrugParser(), singleAdScraperStateFactory().get(State.ReadingDatabase), singleAdScraperStateFactory());
    }

    @Bean
    public martin.dev.pricer.state.Scraper DebenhamsScraper() {
        return new DebenhamsScraper("Debenhams", new martin.dev.pricer.state.scrapers.DebenhamsParser(), singleAdScraperAndJsStateFactory().get(State.ReadingDatabase), singleAdScraperAndJsStateFactory());
    }

    @Bean
    public List<martin.dev.pricer.state.Scraper> scraperList() {
        List<martin.dev.pricer.state.Scraper> scraperList = new ArrayList<>();
        scraperList.add(HSamuelScraper());
        scraperList.add(CreationWatchesScraper());
        scraperList.add(FirstClassWatchesScraper());
        scraperList.add(ErnestJonesScraper());
        scraperList.add(WatchShopScraper());
        scraperList.add(GoldSmithsScraper());
        scraperList.add(TicWatchesScraper());
        scraperList.add(WatchoScraper());
        scraperList.add(SimpkinsJewellersScraper());
        scraperList.add(ArgosScraper());
        scraperList.add(SuperDrugScraper());
        scraperList.add(DebenhamsScraper());
        return scraperList;
    }

    @Scheduled(fixedRate = 40 * 1000)
    public void nudgeScrapers() {
        if (SCRAPING_ON) {
            log.info("Notifying each scraper to fetch URL");
            this.scraperList().forEach(martin.dev.pricer.state.Scraper::fetchUrl);
        }

    }
}