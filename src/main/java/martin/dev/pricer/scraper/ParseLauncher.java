package martin.dev.pricer.scraper;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.model.store.StoreUrl;
import martin.dev.pricer.data.services.store.StoreUrlHandler;
import martin.dev.pricer.scraper.parser.allbeauty.AllBeautyScraper;
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
    private AllBeautyScraper allBeautyScraper;

    public ParseLauncher(StoreUrlHandler storeUrlHandler, HSamuelScraper hSamuelScraper, ErnestJonesScraper ernestJonesScraper, SuperDrugScraper superDrugScraper, ArgosScraper argosScraper, AllBeautyScraper allBeautyScraper) {
        this.storeUrlHandler = storeUrlHandler;
        this.hSamuelScraper = hSamuelScraper;
        this.ernestJonesScraper = ernestJonesScraper;
        this.superDrugScraper = superDrugScraper;
        this.argosScraper = argosScraper;
        this.allBeautyScraper = allBeautyScraper;
    }

    @Scheduled(fixedRate = 60000, initialDelay = 5000)
    public void parse() {
        StoreUrl storeUrl = storeUrlHandler.fetchUrlToScrape(0, 2, 0);

        if (storeUrl == null) {
            log.info("Nothing in database, will check again in 60 seconds.");
            return;
        }
        try {
            storeUrlHandler.setStatusScraping(storeUrl);
            switch (storeUrl.getStore().getName()) {
                case "H. Samuel":
                    hSamuelScraper.scrapePages(storeUrl);
                    break;
                case "Ernest Jones":
                    ernestJonesScraper.scrapePages(storeUrl);
                    break;
                case "Superdrug":
                    superDrugScraper.scrapePages(storeUrl);
                    break;
                case "Argos":
                    argosScraper.scrapePages(storeUrl);
                    break;
                case "All Beauty":
                    allBeautyScraper.scrapePages(storeUrl);
                    break;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            storeUrlHandler.setStatusReady(storeUrl);
            storeUrlHandler.setLastCheckedTimeToNow(storeUrl);
        }
    }
}
