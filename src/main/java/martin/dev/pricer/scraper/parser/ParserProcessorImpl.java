package martin.dev.pricer.scraper.parser;

import martin.dev.pricer.data.fabric.product.ItemPriceProcessor;
import martin.dev.pricer.data.model.store.StoreUrl;

public abstract class ParserProcessorImpl<T extends Factory> implements ParserProcessor {

    private StoreUrl storeUrl;
    private T factory;
    private ItemPriceProcessor itemPriceProcessor;

    public ParserProcessorImpl(ItemPriceProcessor itemPriceProcessor) {
        this.itemPriceProcessor = itemPriceProcessor;
    }

    public final StoreUrl getStoreUrl() {
        return storeUrl;
    }

    public final void setStoreUrl(StoreUrl storeUrl) {
        this.storeUrl = storeUrl;
    }

    public final T getFactory() {
        return factory;
    }

    public final void setFactory(T factory) {
        this.factory = factory;
    }

    public final ItemPriceProcessor getItemPriceProcessor() {
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
