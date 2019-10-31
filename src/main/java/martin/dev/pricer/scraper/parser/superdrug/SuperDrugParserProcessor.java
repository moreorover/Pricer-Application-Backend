package martin.dev.pricer.scraper.parser.superdrug;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.fabric.product.ItemPriceProcessor;
import martin.dev.pricer.data.model.store.StoreUrl;
import martin.dev.pricer.scraper.client.HttpClient;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import martin.dev.pricer.scraper.parser.ParserProcessor;
import org.jsoup.nodes.Document;

import java.util.List;

@Slf4j
public class SuperDrugParserProcessor implements ParserProcessor {

    private StoreUrl storeUrl;
    private SuperDrugFactory superDrugFactory;
    private ItemPriceProcessor itemPriceProcessor;

    public SuperDrugParserProcessor(ItemPriceProcessor itemPriceProcessor) {
        this.itemPriceProcessor = itemPriceProcessor;
    }

    @Override
    public void scrapePages(StoreUrl storeUrl) {
        this.storeUrl = storeUrl;
        initFactory(storeUrl.getUrlLink());
        int maxPageNum = superDrugFactory.getMaxPageNumber();

        for (int i = 0; i < maxPageNum + 1; i++) {
            String nexUrlToScrape = makeNextPageUrl(i);

            log.info("Parsing page: " + nexUrlToScrape);

            List<ParsedItemDto> parsedItemDtos = superDrugFactory.getParsedAds();

            itemPriceProcessor.checkAgainstDatabase(parsedItemDtos, storeUrl);

            initFactory(nexUrlToScrape);
        }
    }

    @Override
    public String makeNextPageUrl(int pageNum) {
        String full = storeUrl.getUrlLink();
        String[] x = full.split("&page=0");
        return x[0] + "&page=0" + pageNum + "&resultsForPage=60&sort=bestBiz";
    }

    @Override
    public void initFactory(String targetUrl) {
        Document document = HttpClient.readContentInJsoupDocument(targetUrl);
        superDrugFactory = new SuperDrugFactory(document);
    }
}
