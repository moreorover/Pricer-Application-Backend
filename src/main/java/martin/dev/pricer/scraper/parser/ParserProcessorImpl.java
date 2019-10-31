package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.fabric.product.ItemPriceProcessor;
import martin.dev.pricer.data.model.store.StoreUrl;

public abstract class ParserProcessorImpl<T extends Factory> implements ParserProcessor {

    private StoreUrl storeUrl;
    private T factory;
    private ItemPriceProcessor itemPriceProcessor;

    public ParserProcessorImpl(ItemPriceProcessor itemPriceProcessor) {
        this.itemPriceProcessor = itemPriceProcessor;
    }

    public StoreUrl getStoreUrl() {
        return storeUrl;
    }

    public void setStoreUrl(StoreUrl storeUrl) {
        this.storeUrl = storeUrl;
    }

    public T getFactory() {
        return factory;
    }

    public void setFactory(T factory) {
        this.factory = factory;
    }

    public ItemPriceProcessor getItemPriceProcessor() {
        return itemPriceProcessor;
    }

    @Override
    public void scrapePages(StoreUrl storeUrl) {
    }

    @Override
    public String makeNextPageUrl(int pageNum) {
        return null;
    }

    @Override
    public void initFactory(String targetUrl) {

    }
}
