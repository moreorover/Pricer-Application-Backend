package martin.dev.pricer.scraper.parser.ernestjones;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.fabric.product.ItemPriceProcessor;
import martin.dev.pricer.data.model.store.StoreUrl;
import martin.dev.pricer.scraper.client.HttpClient;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import martin.dev.pricer.scraper.parser.ParserProcessor;
import org.jsoup.nodes.Document;

import java.util.List;

@Slf4j
public class ErnestJonesParserProcessor implements ParserProcessor {

    private StoreUrl storeUrl;
    private ErnestJonesFactory ernestJonesFactory;
    private ItemPriceProcessor itemPriceProcessor;

    public ErnestJonesParserProcessor(ItemPriceProcessor itemPriceProcessor) {
        this.itemPriceProcessor = itemPriceProcessor;
    }

    @Override
    public void scrapePages(StoreUrl storeUrl) {
        this.storeUrl = storeUrl;
        initFactory(storeUrl.getUrlLink());
        int maxPageNum = ernestJonesFactory.getMaxPageNumber();

        for (int i = 1; i < maxPageNum + 1; i++) {
            String nexUrlToScrape = makeNextPageUrl(i);
            log.info(nexUrlToScrape);

            List<ParsedItemDto> parsedItemDtos = ernestJonesFactory.getParsedAds();

            itemPriceProcessor.checkAgainstDatabase(parsedItemDtos, storeUrl);

            initFactory(nexUrlToScrape);
        }
    }

    @Override
    public String makeNextPageUrl(int pageNum) {
        String full = storeUrl.getUrlLink();
        String[] x = full.split("Pg=");
        return x[0] + "Pg=" + pageNum;
    }

    @Override
    public void initFactory(String targetUrl) {
        Document document = HttpClient.readContentInJsoupDocument(targetUrl);
        ernestJonesFactory = new ErnestJonesFactory(document);
    }


}
