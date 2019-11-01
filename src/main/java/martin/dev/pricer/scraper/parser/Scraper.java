package martin.dev.pricer.scraper.parser;

import martin.dev.pricer.data.fabric.product.ItemPriceProcessor;
import martin.dev.pricer.data.model.store.StoreUrl;
import martin.dev.pricer.scraper.client.HttpClient;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class Scraper {

    @Autowired
    private ItemPriceProcessor itemPriceProcessor;
    private StoreUrl storeUrl;
    private Document pageContentInJsoupHtml;

    public ItemPriceProcessor getItemPriceProcessor() {
        return itemPriceProcessor;
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

    public abstract void scrapePages(StoreUrl storeUrl);

    public abstract String makeNextPageUrl(int pageNum);

    public void initFactory(String targetUrl) {
        pageContentInJsoupHtml = HttpClient.readContentInJsoupDocument(targetUrl);
    }
}