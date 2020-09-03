package martin.dev.pricer.profile;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.repository.*;
import martin.dev.pricer.data.service.*;
import martin.dev.pricer.discord.DiscordService;
import martin.dev.pricer.scraper.*;
import martin.dev.pricer.scraper.parser.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.security.auth.login.LoginException;

@Profile("remote-rdp-prod")
@Configuration
@Slf4j
public class RemoteRdpProdProfile {

    private final StatusRepository statusRepository;
    private final UrlRepository urlRepository;
    private final ItemRepository itemRepository;
    private final ParserErrorRepository parserErrorRepository;
    private final DealRepository dealRepository;

    @Value("${discord.api.key}")
    private String DiscordApiKey;

    public RemoteRdpProdProfile(StatusRepository statusRepository, UrlRepository urlRepository, ItemRepository itemRepository, ParserErrorRepository parserErrorRepository, DealRepository dealRepository) {
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
}