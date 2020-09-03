package martin.dev.pricer.profile;

import martin.dev.pricer.data.repository.*;
import martin.dev.pricer.data.service.*;
import martin.dev.pricer.discord.DiscordService;
import martin.dev.pricer.scraper.Scraper;
import martin.dev.pricer.scraper.*;
import martin.dev.pricer.scraper.parser.ArgosParser;
import martin.dev.pricer.scraper.parser.CreationWatchesParser;
import martin.dev.pricer.scraper.parser.ErnestJonesParser;
import martin.dev.pricer.scraper.parser.FirstClassWatchesParser;
import martin.dev.pricer.scraper.parser.HSamuelParser;
import martin.dev.pricer.scraper.parser.GoldSmithsParser;
import martin.dev.pricer.scraper.parser.*;
import martin.dev.pricer.scraper.parser.SimpkinsJewellersParser;
import martin.dev.pricer.scraper.parser.TicWatchesParser;
import martin.dev.pricer.scraper.parser.WatchShopParser;
import martin.dev.pricer.scraper.parser.WatchoParser;
import martin.dev.pricer.state.*;
import martin.dev.pricer.state.scrapers.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Profile("local-rdp-prod")
@Configuration
public class LocalRdpProdProfile {

    private final StatusRepository statusRepository;
    private final UrlRepository urlRepository;
    private final ItemRepository itemRepository;
    private final ParserErrorRepository parserErrorRepository;
    private final DealRepository dealRepository;

    @Value("${discord.api.key}")
    private String DiscordApiKey;

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
    public ScraperSubject scraperSubject() {
        return new ScraperSubject();
    }

    @Bean
    public AbstractParser AMJWatchesParser() {
        return new AMJWatchesParser(getParserValidator());
    }

    @Bean
    public AbstractParser ArgosParser() {
        return new ArgosParser(getParserValidator());
    }

    @Bean
    public AbstractParser CreationWatchesParser() {
        return new CreationWatchesParser(getParserValidator());
    }

    @Bean
    public AbstractParser DebenhamsParser() {
        return new DebenhamsParser(getParserValidator());
    }

    @Bean
    public AbstractParser ErnestJonesParser() {
        return new ErnestJonesParser(getParserValidator());
    }

    @Bean
    public AbstractParser FirstClassWatchesParser() {
        return new FirstClassWatchesParser(getParserValidator());
    }

    @Bean
    public AbstractParser GoldSmithsParser() {
        return new GoldSmithsParser(getParserValidator());
    }

    @Bean
    public AbstractParser HSamuelParser() {
        return new HSamuelParser(getParserValidator());
    }

    @Bean
    public AbstractParser SuperDrugParser() {
        return new SuperDrugParser(getParserValidator());
    }

    @Bean
    public AbstractParser TicWatchesParser() {
        return new TicWatchesParser(getParserValidator());
    }

    @Bean
    public AbstractParser WatchoParser() {
        return new WatchoParser(getParserValidator());
    }

    @Bean
    public AbstractParser WatchShopParser() {
        return new WatchShopParser(getParserValidator());
    }

    @Bean
    public AbstractParser SimpkinsJewellersParser() {
        return new SimpkinsJewellersParser(getParserValidator());
    }

    @Bean
    public ScraperSubject getSubject() {
        ScraperSubject subject = new ScraperSubject();
        new Scraper(subject, AMJWatchesParser(), getSqlItemService());
        new Scraper(subject, ArgosParser(), getSqlItemService());
        new Scraper(subject, CreationWatchesParser(), getSqlItemService());
        new Scraper(subject, DebenhamsParser(), getSqlItemService());
        new Scraper(subject, ErnestJonesParser(), getSqlItemService());
        new Scraper(subject, FirstClassWatchesParser(), getSqlItemService());
        new Scraper(subject, GoldSmithsParser(), getSqlItemService());
        new Scraper(subject, HSamuelParser(), getSqlItemService());
        new Scraper(subject, SuperDrugParser(), getSqlItemService());
        new Scraper(subject, TicWatchesParser(), getSqlItemService());
        new Scraper(subject, WatchoParser(), getSqlItemService());
        new Scraper(subject, WatchShopParser(), getSqlItemService());
        new Scraper(subject, SimpkinsJewellersParser(), getSqlItemService());
        return subject;
    }

    @Bean
    public ParserValidator getParserValidator() {
        return new JsoupValidator(getParserErrorService());
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
    public Launcher runner() {
        return new Launcher(getSqlStatusService(), getSqlUrlService(), getDealService(), getSubject(), discordBot());
    }

    @Bean
    public ScraperReadingState scraperReadingState() {
        return new ScraperReadingState(this.getSqlStatusService(), this.getSqlUrlService());
    }

    @Bean
    public ScraperFetchingHtmlAndJsState scraperFetchingHtmlAndJsState() {
        return new ScraperFetchingHtmlAndJsState();
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
        availableStates.put(State.FetchingHtml, scraperFetchingHtmlAndJsState());
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
        return scraperList;
    }

    @Bean
    public Begin begin() {
        return new Begin(scraperList());
    }
}