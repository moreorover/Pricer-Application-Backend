package martin.dev.pricer.scraper.parser;

import martin.dev.pricer.data.model.store.StoreUrl;

public interface ParserProcessor {

    void scrapePages(StoreUrl storeUrl);

    String makeNextPageUrl(int pageNum);

    void initFactory(String targetUrl);
}
