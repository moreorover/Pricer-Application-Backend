package martin.dev.pricer.scraper.parser.argos;

import martin.dev.pricer.data.model.store.StoreUrl;
import martin.dev.pricer.scraper.parser.ParserProcessor;

public class ArgosParserProcessor implements ParserProcessor {



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
