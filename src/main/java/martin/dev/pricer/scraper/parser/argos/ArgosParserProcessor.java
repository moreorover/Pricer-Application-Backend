package martin.dev.pricer.scraper.parser.argos;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.fabric.product.ItemPriceProcessor;
import martin.dev.pricer.data.model.store.StoreUrl;
import martin.dev.pricer.scraper.client.HttpClient;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import martin.dev.pricer.scraper.parser.ParserProcessorImpl;
import org.jsoup.nodes.Document;

import java.util.List;

@Slf4j
public class ArgosParserProcessor extends ParserProcessorImpl<ArgosFactory> {

    public ArgosParserProcessor(ItemPriceProcessor itemPriceProcessor) {
        super(itemPriceProcessor);
    }

    @Override
    public void scrapePages(StoreUrl storeUrl) {
        setStoreUrl(storeUrl);
        initFactory(storeUrl.getUrlLink());
        int maxPageNum = getFactory().getMaxPageNumber();

        int currentRotation = 1;

        while (currentRotation <= maxPageNum){
            log.info("Parsing page: " + makeNextPageUrl(currentRotation));

            List<ParsedItemDto> parsedItemDtos = getFactory().getParsedAds();

            getItemPriceProcessor().checkAgainstDatabase(parsedItemDtos, storeUrl);

            String nexUrlToScrape = makeNextPageUrl(++currentRotation);
            initFactory(nexUrlToScrape);
        }
    }

    @Override
    public String makeNextPageUrl(int pageNum) {
        String full = getStoreUrl().getUrlLink();
        String[] x = full.split("/page:");
        return x[0] + "/page:" + pageNum;
    }

    @Override
    public void initFactory(String targetUrl) {
        Document document = HttpClient.readContentInJsoupDocument(targetUrl);
        setFactory(new ArgosFactory(document));
    }
}
