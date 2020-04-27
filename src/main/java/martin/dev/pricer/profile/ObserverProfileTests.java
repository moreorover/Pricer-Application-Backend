package martin.dev.pricer.profile;

import martin.dev.pricer.data.repository.DealRepository;
import martin.dev.pricer.data.repository.ItemRepository;
import martin.dev.pricer.data.repository.ParserErrorRepository;
import martin.dev.pricer.data.repository.StoreRepository;
import martin.dev.pricer.data.service.ItemService;
import martin.dev.pricer.data.service.ParserErrorService;
import martin.dev.pricer.data.service.StoreService;
import martin.dev.pricer.discord.BotSendMessage;
import martin.dev.pricer.scraper.Launcher;
import martin.dev.pricer.scraper.ParserHandler;
import martin.dev.pricer.scraper.Scraper;
import martin.dev.pricer.scraper.ScraperSubject;
import martin.dev.pricer.scraper.parser.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.security.auth.login.LoginException;

@Profile("testscollection")
@Configuration
public class ObserverProfileTests {

    private StoreRepository storeRepository;
    private ItemRepository itemRepository;
    private DealRepository dealRepository;
    private ParserErrorRepository parserErrorRepository;

    public ObserverProfileTests(StoreRepository storeRepository, ItemRepository itemRepository, DealRepository dealRepository, ParserErrorRepository parserErrorRepository) {
        this.storeRepository = storeRepository;
        this.itemRepository = itemRepository;
        this.dealRepository = dealRepository;
        this.parserErrorRepository = parserErrorRepository;
    }

    @Bean
    public ItemService getMongoItemService() {
        return new ItemService(itemRepository, dealRepository, getBotSendMessage());
    }

    @Bean
    public StoreService getMongoStoreService() {
        return new StoreService(storeRepository);
    }

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
        new Scraper(subject, AMJWatchesParser(), getMongoItemService(), 1);
        new Scraper(subject, ArgosParser(), getMongoItemService(), 1);
        new Scraper(subject, CreationWatchesParser(), getMongoItemService(), 1);
        new Scraper(subject, DebenhamsParser(), getMongoItemService(), 1);
        new Scraper(subject, ErnestJonesParser(), getMongoItemService(), 1);
        new Scraper(subject, FirstClassWatchesParser(), getMongoItemService(), 1);
        new Scraper(subject, GoldSmithsParser(), getMongoItemService(), 1);
        new Scraper(subject, HSamuelParser(), getMongoItemService(), 1);
        new Scraper(subject, SuperDrugParser(), getMongoItemService(), 0);
        new Scraper(subject, TicWatchesParser(), getMongoItemService(), 1);
        new Scraper(subject, WatchoParser(), getMongoItemService(), 1);
        new Scraper(subject, WatchShopParser(), getMongoItemService(), 1);
        new Scraper(subject, SimpkinsJewellersParser(), getMongoItemService(), 1);
        return subject;
    }

    @Bean
    public JDA jda(){
        JDA jda = null;
        try {
            //TODO move API key to properties file
            jda = new JDABuilder("NTU5NDg4NDM4OTQwNzI5MzQ1.XqYAkg.ydO0bxKt1xBSY5UQHi9VnPcFA1I")
                    .build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
        return jda;
    }

    @Bean
    public BotSendMessage getBotSendMessage() {
        return new BotSendMessage(jda());
    }

    @Bean
    public Launcher runner() {
        return new Launcher(getMongoStoreService(), getSubject());
    }
}
