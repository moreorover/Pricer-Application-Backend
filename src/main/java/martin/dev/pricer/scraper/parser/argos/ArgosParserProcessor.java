package martin.dev.pricer.scraper.parser.argos;

import martin.dev.pricer.data.fabric.product.ItemPriceProcessor;
import martin.dev.pricer.data.model.store.StoreUrl;
import martin.dev.pricer.scraper.parser.ParserProcessorImpl;

public class ArgosParserProcessor extends ParserProcessorImpl<ArgosFactory> {

    public ArgosParserProcessor(ItemPriceProcessor itemPriceProcessor) {
        super(itemPriceProcessor);
    }

    @Override
    public void scrapePages(StoreUrl storeUrl) {
        super.scrapePages(storeUrl);
    }

    @Override
    public String makeNextPageUrl(int pageNum) {
        return super.makeNextPageUrl(pageNum);
    }

    @Override
    public void initFactory(String targetUrl) {
        super.initFactory(targetUrl);
    }
}
