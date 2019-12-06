package martin.dev.pricer.scraper.parser;

import martin.dev.pricer.data.fabric.product.DealProcessor;
import martin.dev.pricer.data.fabric.product.ItemPriceProcessor;
import martin.dev.pricer.data.model.store.StoreUrl;
import martin.dev.pricer.scraper.client.HttpClient;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class Scraper {

    @Autowired
    private DealProcessor dealProcessor;
    private StoreUrl storeUrl;
    private Document pageContentInJsoupHtml;

    public DealProcessor getDealProcessor() {
        return dealProcessor;
    }

    public StoreUrl getStoreUrl() {
        return storeUrl;
    }

    public void setStoreUrl(StoreUrl storeUrl) {
        this.storeUrl = storeUrl;
    }

    public Document getPageContentInJsoupHtml() {
        return pageContentInJsoupHtml;
    }

    public void setPageContentInJsoupHtml(Document pageContentInJsoupHtml) {
        this.pageContentInJsoupHtml = pageContentInJsoupHtml;
    }

    public abstract void scrapePages(StoreUrl storeUrl);

    public abstract String makeNextPageUrl(int pageNum);

    public void initFactory(String targetUrl) {
        pageContentInJsoupHtml = HttpClient.readContentInJsoupDocument(targetUrl);
    }
}
