package martin.dev.pricer.profile;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.repository.DealRepository;
import martin.dev.pricer.data.repository.ItemRepository;
import martin.dev.pricer.data.repository.StatusRepository;
import martin.dev.pricer.data.repository.UrlRepository;
import martin.dev.pricer.data.service.DealService;
import martin.dev.pricer.data.service.ItemService;
import martin.dev.pricer.data.service.StatusService;
import martin.dev.pricer.data.service.UrlService;
import martin.dev.pricer.discord.DiscordService;
import martin.dev.pricer.scraper.*;
import martin.dev.pricer.scraper.scrapers.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@Slf4j
public class LocalRdpProdProfile {

    private final StatusRepository statusRepository;
    private final UrlRepository urlRepository;
    private final ItemRepository itemRepository;
    private final DealRepository dealRepository;

    @Value("${discord.api.key}")
    private String DiscordApiKey;

    @Value("${price.scraping.on}")
    private boolean SCRAPING_ON;

    public LocalRdpProdProfile(StatusRepository statusRepository, UrlRepository urlRepository, ItemRepository itemRepository, DealRepository dealRepository) {
        this.statusRepository = statusRepository;
        this.urlRepository = urlRepository;
        this.itemRepository = itemRepository;
        this.dealRepository = dealRepository;
    }

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(1);
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
    public DataReader getRepoDataReader() {
        return new RepoDataReader(this.getSqlStatusService(), this.getSqlUrlService());
    }

    @Bean
    DataProcessor getSimpleDataProcessor() {
        return new SimpleDataProcessor(this.getSqlItemService());
    }

    @Bean
    DataWriter getDataWriter() {
        return new RepoDataWriter(this.getSqlStatusService(), this.getSqlUrlService());
    }

    @Bean
    WebClient getJsoupWebClient() {
        return new JsoupWebClient();
    }

    @Bean
    WebClient getSeleniumWebClient() {
        return new SeleniumWebClient(false);
    }

    @Bean DataSender getDataSender() {
        return new DiscordDataSender(this.getDealService(), this.discordBot());
    }

    @Bean
    public Scraper HSamuelScraper() {
        return new HSamuelScraper(this.getJsoupWebClient(), getRepoDataReader(), new HSamuelParser(), this.getSimpleDataProcessor(), this.getDataWriter(), this.getDataSender());
    }

    @Bean
    public Scraper CreationWatchesScraper() {
        return new CreationWatchesScraper(this.getJsoupWebClient(), getRepoDataReader(), new CreationWatchesParser(), this.getSimpleDataProcessor(), this.getDataWriter(), this.getDataSender());
    }

    @Bean
    public Scraper FirstClassWatchesScraper() {
        return new FirstClassWatchesScraper(this.getJsoupWebClient(), getRepoDataReader(), new FirstClassWatchesParser(), this.getSimpleDataProcessor(), this.getDataWriter(), this.getDataSender());
    }

    @Bean
    public Scraper ErnestJonesScraper() {
        return new ErnestJonesScraper(this.getJsoupWebClient(), getRepoDataReader(), new ErnestJonesParser(), this.getSimpleDataProcessor(), this.getDataWriter(), this.getDataSender());
    }

    @Bean
    public Scraper WatchShopScraper() {
        return new WatchShopScraper(this.getJsoupWebClient(), getRepoDataReader(), new WatchShopParser(), this.getSimpleDataProcessor(), this.getDataWriter(), this.getDataSender());
    }

    @Bean
    public Scraper GoldSmithsScraper() {
        return new GoldSmithsScraper(this.getJsoupWebClient(), getRepoDataReader(), new GoldSmithsParser(), this.getSimpleDataProcessor(), this.getDataWriter(), this.getDataSender());
    }

    @Bean
    public Scraper TicWatchesScraper() {
        return new TicWatchesScraper(this.getJsoupWebClient(), getRepoDataReader(), new TicWatchesParser(), this.getSimpleDataProcessor(), this.getDataWriter(), this.getDataSender());
    }

    @Bean
    public Scraper WatchoScraper() {
        return new WatchoScraper(this.getJsoupWebClient(), getRepoDataReader(), new WatchoParser(), this.getSimpleDataProcessor(), this.getDataWriter(), this.getDataSender());
    }

    @Bean
    public Scraper SimpkinsJewellersScraper() {
        return new SimpkinsJewellersScraper(this.getJsoupWebClient(), getRepoDataReader(), new SimpkinsJewellersParser(), this.getSimpleDataProcessor(), this.getDataWriter(), this.getDataSender());
    }

    @Bean
    public Scraper ArgosScraper() {
        return new ArgosScraper(this.getJsoupWebClient(), getRepoDataReader(), new ArgosParser(), this.getSimpleDataProcessor(), this.getDataWriter(), this.getDataSender());
    }

    @Bean
    public Scraper SuperDrugScraper() {
        return new SuperDrugScraper(this.getSeleniumWebClient(), getRepoDataReader(), new SuperDrugParser(), this.getSimpleDataProcessor(), this.getDataWriter(), this.getDataSender());
    }

    @Bean
    public Scraper DebenhamsScraper() {
        return new DebenhamsScraper(this.getSeleniumWebClient(), getRepoDataReader(), new DebenhamsParser(), this.getSimpleDataProcessor(), this.getDataWriter(), this.getDataSender());
    }

    @Bean
    public List<Scraper> scraperList() {
        List<Scraper> scraperList = new ArrayList<>();
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

    @Scheduled(fixedRate = 40 * 1000, initialDelay = 5 * 1000)
    public void nudgeScrapers() {
        if (SCRAPING_ON) {
            log.info("Notifying each scraper to fetch URL");
            this.scraperList().forEach(Scraper::fetchUrl);
        }

    }
}