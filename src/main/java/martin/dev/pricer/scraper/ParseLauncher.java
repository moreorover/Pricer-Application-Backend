package martin.dev.pricer.scraper;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.model.store.StoreUrl;
import martin.dev.pricer.data.services.store.StoreUrlHandler;
import martin.dev.pricer.scraper.parser.argos.ArgosScraper;
import martin.dev.pricer.scraper.parser.ernestjones.ErnestJonesScraper;
import martin.dev.pricer.scraper.parser.hsamuel.HSamuelScraper;
import martin.dev.pricer.scraper.parser.superdrug.SuperDrugScraper;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
public class ParseLauncher {

    private StoreUrlHandler storeUrlHandler;
    private HSamuelScraper hSamuelScraper;
    private ErnestJonesScraper ernestJonesScraper;
    private SuperDrugScraper superDrugScraper;
    private ArgosScraper argosScraper;

    public ParseLauncher(StoreUrlHandler storeUrlHandler, HSamuelScraper hSamuelScraper, ErnestJonesScraper ernestJonesScraper, SuperDrugScraper superDrugScraper, ArgosScraper argosScraper) {
        this.storeUrlHandler = storeUrlHandler;
        this.hSamuelScraper = hSamuelScraper;
        this.ernestJonesScraper = ernestJonesScraper;
        this.superDrugScraper = superDrugScraper;
        this.argosScraper = argosScraper;
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
                    hSamuelScraper.scrapePages(storeUrl);
                    break;
                case "Ernest Jones":
                    storeUrlHandler.setStatusScraping(storeUrl);
                    ernestJonesScraper.scrapePages(storeUrl);
                    break;
                case "Superdrug":
                    storeUrlHandler.setStatusScraping(storeUrl);
                    superDrugScraper.scrapePages(storeUrl);
                    break;
                case "Argos":
                    storeUrlHandler.setStatusScraping(storeUrl);
                    argosScraper.scrapePages(storeUrl);
                    break;
            }

            storeUrlHandler.setStatusReady(storeUrl);
            storeUrlHandler.setLastCheckedTimeToNow(storeUrl);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
