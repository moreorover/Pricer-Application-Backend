package martin.dev.pricer.scraper.parser.hsamuel;

import martin.dev.pricer.data.model.store.StoreUrl;
import martin.dev.pricer.scraper.client.HttpClient;
import org.jsoup.nodes.Document;

public class HSamuelParserProcessor {

    private StoreUrl storeUrl;
    private Document pageContentInJsoup;
    private HSamuelPage hSamuelPage;

    public void fetchPageContentFromWeb(){
        pageContentInJsoup = HttpClient.readContentInJsoupDocument(storeUrl.getUrlLink());
        hSamuelPage = new HSamuelPage(pageContentInJsoup);
    }

    public int fetchMaxPageToScrape(){
        hSamuelPage.parseMaxPageNum();
        return hSamuelPage.getMaxPageNum();
    }

    public void scrapePages(int maxPageNum){
        for (int i = 1; i < maxPageNum + 1; i++){
            String nexUrlToScrape = makeNextPageUrl(i);

            System.out.println(nexUrlToScrape);

            HSamuelParserProcessor hSamuelParserProcessor = new HSamuelParserProcessor();
        }

    }

    public String makeNextPageUrl(int pageNum){
        String full = storeUrl.getUrlLink();
        String[] x = full.split("Pg=");
        return x[0] + "Pg=" + pageNum;
    }


}
