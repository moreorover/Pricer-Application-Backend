package martin.dev.pricer.scraper.parser.superdrug;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.fabric.product.ItemPriceProcessor;
import martin.dev.pricer.data.model.store.StoreUrl;
import martin.dev.pricer.scraper.client.HttpClient;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import martin.dev.pricer.scraper.parser.ParserProcessorImpl;
import org.jsoup.nodes.Document;

import java.util.List;

@Slf4j
public class SuperDrugParserProcessor extends ParserProcessorImpl<SuperDrugFactory> {

    public SuperDrugParserProcessor(ItemPriceProcessor itemPriceProcessor) {
        super(itemPriceProcessor);
    }

    @Override
    public void scrapePages(StoreUrl storeUrl) {
        this.setStoreUrl(storeUrl);
        initFactory(storeUrl.getUrlLink());
        int maxPageNum = getFactory().getMaxPageNumber();

        for (int i = 0; i < maxPageNum + 1; i++) {
            String nexUrlToScrape = makeNextPageUrl(i);

            log.info("Parsing page: " + nexUrlToScrape);

            List<ParsedItemDto> parsedItemDtos = getFactory().getParsedAds();

            getItemPriceProcessor().checkAgainstDatabase(parsedItemDtos, storeUrl);

            initFactory(nexUrlToScrape);
        }
    }

    @Override
    public String makeNextPageUrl(int pageNum) {
        String full = getStoreUrl().getUrlLink();
        String[] x = full.split("&page=0");
        return x[0] + "&page=0" + pageNum + "&resultsForPage=60&sort=bestBiz";
    }

    @Override
    public void initFactory(String targetUrl) {
        Document document = HttpClient.readContentInJsoupDocument(targetUrl);
        setFactory(new SuperDrugFactory(document));
    }
}
