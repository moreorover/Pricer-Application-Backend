package martin.dev.pricer.scraper.parser.hsamuel;

import martin.dev.pricer.data.fabric.product.ItemPriceProcessor;
import martin.dev.pricer.data.model.dto.parse.ParsedItemDto;
import martin.dev.pricer.data.model.store.StoreUrl;
import martin.dev.pricer.scraper.client.HttpClient;
import org.jsoup.nodes.Document;

import java.util.List;

public class HSamuelParserProcessor {

    private StoreUrl storeUrl;
    private HSamuelFactory hSamuelFactory;
    private ItemPriceProcessor itemPriceProcessor;

    public HSamuelParserProcessor(ItemPriceProcessor itemPriceProcessor) {
        this.itemPriceProcessor = itemPriceProcessor;
    }

    public void scrapePages(StoreUrl storeUrl) {
        this.storeUrl = storeUrl;
        initFactory(storeUrl.getUrlLink());
        int maxPageNum = hSamuelFactory.getMaxPageNumber();

        for (int i = 1; i < maxPageNum + 1; i++) {
            String nexUrlToScrape = makeNextPageUrl(i);
            System.out.println(nexUrlToScrape);

            List<ParsedItemDto> parsedItemDtos = hSamuelFactory.getParsedAds();

//            TODO call method to deal with parsed ads
            itemPriceProcessor.checkAgainstDatabase(parsedItemDtos, storeUrl.getStore());

            initFactory(nexUrlToScrape);

        }

    }

    public String makeNextPageUrl(int pageNum) {
        String full = storeUrl.getUrlLink();
        String[] x = full.split("Pg=");
        return x[0] + "Pg=" + pageNum;
    }

    public void initFactory(String targetUrl) {
        Document document = HttpClient.readContentInJsoupDocument(targetUrl);
        hSamuelFactory = new HSamuelFactory(document);
    }


}
