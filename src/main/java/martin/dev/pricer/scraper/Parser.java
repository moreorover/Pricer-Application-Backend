package martin.dev.pricer.scraper;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.model.store.StoreUrl;
import martin.dev.pricer.data.services.store.StoreUrlHandler;
import martin.dev.pricer.scraper.parser.argos.ArgosService;
import martin.dev.pricer.scraper.parser.ernestjones.ErnestJonesParserProcessor;
import martin.dev.pricer.scraper.parser.hsamuel.HSamuelParserProcessor;
import martin.dev.pricer.scraper.parser.superdrug.SuperDrugParserProcessor;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
public class Parser {

    private StoreUrlHandler storeUrlHandler;
    private HSamuelParserProcessor hSamuelParserProcessor;
    private ErnestJonesParserProcessor ernestJonesParserProcessor;
    private SuperDrugParserProcessor superDrugParserProcessor;
    private ArgosService argosService;

    public Parser(StoreUrlHandler storeUrlHandler, HSamuelParserProcessor hSamuelParserProcessor, ErnestJonesParserProcessor ernestJonesParserProcessor, SuperDrugParserProcessor superDrugParserProcessor, ArgosService argosService) {
        this.storeUrlHandler = storeUrlHandler;
        this.hSamuelParserProcessor = hSamuelParserProcessor;
        this.ernestJonesParserProcessor = ernestJonesParserProcessor;
        this.superDrugParserProcessor = superDrugParserProcessor;
        this.argosService = argosService;
    }

    @Scheduled(fixedRate = 60000, initialDelay = 5000)
    public void parse() {
        StoreUrl storeUrl = storeUrlHandler.fetchUrlToScrape(0, 2, 0);

        if (storeUrl == null) {
            log.info("Nothing in database, will check again in 60 seconds.");
            return;
        }
        try {
            switch (storeUrl.getStore().getName()) {
                case "H. Samuel":
                    storeUrlHandler.setStatusScraping(storeUrl);
                    hSamuelParserProcessor.scrapePages(storeUrl);
                    break;
                case "Ernest Jones":
                    storeUrlHandler.setStatusScraping(storeUrl);
                    ernestJonesParserProcessor.scrapePages(storeUrl);
                    break;
                case "Superdrug":
                    storeUrlHandler.setStatusScraping(storeUrl);
                    superDrugParserProcessor.scrapePages(storeUrl);
                    break;
                case "Argos":
                    storeUrlHandler.setStatusScraping(storeUrl);
                    argosService.scrapePages(storeUrl);
                    break;
            }

            storeUrlHandler.setStatusReady(storeUrl);
            storeUrlHandler.setLastCheckedTimeToNow(storeUrl);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
