package martin.dev.pricer.profile;

import martin.dev.pricer.data.repository.ParserErrorRepository;
import martin.dev.pricer.data.service.ParserErrorService;
import martin.dev.pricer.flyway.repository.ItemRepositoryFlyway;
import martin.dev.pricer.flyway.repository.StatusRepositoryFlyway;
import martin.dev.pricer.flyway.repository.UrlRepositoryFlyway;
import martin.dev.pricer.flyway.service.ItemServiceFlyway;
import martin.dev.pricer.flyway.service.StatusServiceFlyway;
import martin.dev.pricer.flyway.service.UrlServiceFlyway;
import martin.dev.pricer.scraper.ParserHandler;
import martin.dev.pricer.scraper.flyway.LauncherFlyway;
import martin.dev.pricer.scraper.flyway.Scraper;
import martin.dev.pricer.scraper.flyway.ScraperSubject;
import martin.dev.pricer.scraper.parser.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("sqldatabase")
@Configuration
public class SQLDatabaseProfile {

    private StatusRepositoryFlyway statusRepositoryFlyway;
    private UrlRepositoryFlyway urlRepositoryFlyway;
    private ItemRepositoryFlyway itemRepositoryFlyway;
    private ParserErrorRepository parserErrorRepository;

    public SQLDatabaseProfile(StatusRepositoryFlyway statusRepositoryFlyway, UrlRepositoryFlyway urlRepositoryFlyway, ItemRepositoryFlyway itemRepositoryFlyway, ParserErrorRepository parserErrorRepository) {
        this.statusRepositoryFlyway = statusRepositoryFlyway;
        this.urlRepositoryFlyway = urlRepositoryFlyway;
        this.itemRepositoryFlyway = itemRepositoryFlyway;
        this.parserErrorRepository = parserErrorRepository;
    }

    @Bean
    public ItemServiceFlyway getSqlItemService() {
        return new ItemServiceFlyway(itemRepositoryFlyway);
    }

    @Bean
    public StatusServiceFlyway getSqlStatusService() { return new StatusServiceFlyway(statusRepositoryFlyway); }

    @Bean
    public UrlServiceFlyway getSqlUrlService() { return new UrlServiceFlyway(urlRepositoryFlyway); }

    @Bean
    public ParserErrorService getParserErrorService() {
        return new ParserErrorService(parserErrorRepository);
    }

    @Bean
    public ScraperSubject scraperSubject(){
        return new ScraperSubject();
    }

    @Bean
    public ParserHandler AMJWatchesParser(){
        return new ParserHandler(new AMJWatchesParser(), getParserErrorService());
    }

    @Bean
    public ParserHandler ArgosParser(){
        return new ParserHandler(new ArgosParser(), getParserErrorService());
    }

    @Bean
    public ParserHandler CreationWatchesParser(){
        return new ParserHandler(new CreationWatchesParser(), getParserErrorService());
    }

    @Bean
    public ParserHandler DebenhamsParser(){
        return new ParserHandler(new DebenhamsParser(), getParserErrorService());
    }

    @Bean
    public ParserHandler ErnestJonesParser(){
        return new ParserHandler(new ErnestJonesParser(), getParserErrorService());
    }

    @Bean
    public ParserHandler FirstClassWatchesParser(){
        return new ParserHandler(new FirstClassWatchesParser(), getParserErrorService());
    }

    @Bean
    public ParserHandler GoldSmithsParser(){
        return new ParserHandler(new GoldSmithsParser(), getParserErrorService());
    }

    @Bean
    public ParserHandler HSamuelParser(){
        return new ParserHandler(new HSamuelParser(), getParserErrorService());
    }

    @Bean
    public ParserHandler SuperDrugParser(){
        return new ParserHandler(new SuperDrugParser(), getParserErrorService());
    }

    @Bean
    public ParserHandler TicWatchesParser(){
        return new ParserHandler(new TicWatchesParser(), getParserErrorService());
    }

    @Bean
    public ParserHandler WatchoParser(){
        return new ParserHandler(new WatchoParser(), getParserErrorService());
    }

    @Bean
    public ParserHandler WatchShopParser(){
        return new ParserHandler(new WatchShopParser(), getParserErrorService());
    }

    @Bean
    public ParserHandler SimpkinsJewellersParser(){
        return new ParserHandler(new SimpkinsJewellersParser(), getParserErrorService());
    }

    @Bean
    public ScraperSubject getSubject() {
        ScraperSubject subject = new ScraperSubject();
        new Scraper(subject, AMJWatchesParser(), getSqlItemService(), 1);
        new Scraper(subject, ArgosParser(), getSqlItemService(), 1);
        new Scraper(subject, CreationWatchesParser(), getSqlItemService(), 1);
        new Scraper(subject, DebenhamsParser(), getSqlItemService(), 1);
        new Scraper(subject, ErnestJonesParser(), getSqlItemService(), 1);
        new Scraper(subject, FirstClassWatchesParser(), getSqlItemService(), 1);
        new Scraper(subject, GoldSmithsParser(), getSqlItemService(), 1);
        new Scraper(subject, HSamuelParser(), getSqlItemService(), 1);
        new Scraper(subject, SuperDrugParser(), getSqlItemService(), 0);
        new Scraper(subject, TicWatchesParser(), getSqlItemService(), 1);
        new Scraper(subject, WatchoParser(), getSqlItemService(), 1);
        new Scraper(subject, WatchShopParser(), getSqlItemService(), 1);
        new Scraper(subject, SimpkinsJewellersParser(), getSqlItemService(), 1);
        return subject;
    }

    @Bean
    public LauncherFlyway runner() {
        return new LauncherFlyway(getSqlStatusService(), getSqlUrlService(), getSubject());
    }
}
