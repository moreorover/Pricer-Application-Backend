package martin.dev.pricer.profile;

import martin.dev.pricer.flyway.repository.ItemRepositoryFlyway;
import martin.dev.pricer.flyway.repository.ParserErrorRepositoryFlyway;
import martin.dev.pricer.flyway.repository.StatusRepositoryFlyway;
import martin.dev.pricer.flyway.repository.UrlRepositoryFlyway;
import martin.dev.pricer.flyway.service.ItemServiceFlyway;
import martin.dev.pricer.flyway.service.ParserErrorServiceFlyway;
import martin.dev.pricer.flyway.service.StatusServiceFlyway;
import martin.dev.pricer.flyway.service.UrlServiceFlyway;
import martin.dev.pricer.scraper.AbstractParser;
import martin.dev.pricer.scraper.JsoupValidator;
import martin.dev.pricer.scraper.ParserValidator;
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
    private ParserErrorRepositoryFlyway parserErrorRepositoryFlyway;

    public SQLDatabaseProfile(StatusRepositoryFlyway statusRepositoryFlyway, UrlRepositoryFlyway urlRepositoryFlyway, ItemRepositoryFlyway itemRepositoryFlyway, ParserErrorRepositoryFlyway parserErrorRepositoryFlyway) {
        this.statusRepositoryFlyway = statusRepositoryFlyway;
        this.urlRepositoryFlyway = urlRepositoryFlyway;
        this.itemRepositoryFlyway = itemRepositoryFlyway;
        this.parserErrorRepositoryFlyway = parserErrorRepositoryFlyway;
    }

    @Bean
    public ItemServiceFlyway getSqlItemService() {
        return new ItemServiceFlyway(itemRepositoryFlyway);
    }

    @Bean
    public StatusServiceFlyway getSqlStatusService() {
        return new StatusServiceFlyway(statusRepositoryFlyway);
    }

    @Bean
    public UrlServiceFlyway getSqlUrlService() {
        return new UrlServiceFlyway(urlRepositoryFlyway);
    }

    @Bean
    public ParserErrorServiceFlyway getParserErrorService() {
        return new ParserErrorServiceFlyway(parserErrorRepositoryFlyway);
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
    public LauncherFlyway runner() {
        return new LauncherFlyway(getSqlStatusService(), getSqlUrlService(), getSubject());
    }
}
