package martin.dev.pricer.scraper.parser.argos;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.fabric.product.ItemPriceProcessor;
import martin.dev.pricer.data.model.store.StoreUrl;
import martin.dev.pricer.scraper.client.HttpClient;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ArgosService {

    @Autowired
    private ArgosParser argosParser;

    private ItemPriceProcessor itemPriceProcessor;

    private StoreUrl storeUrl;
    private Document pageContentInJsoupHtml;

    public ArgosService(ItemPriceProcessor itemPriceProcessor) {
        this.itemPriceProcessor = itemPriceProcessor;
    }

    public void scrapePages(StoreUrl storeUrl) {
        this.storeUrl = storeUrl;
        initFactory(storeUrl.getUrlLink());
        int maxPageNum = argosParser.parseMaxPageNum(pageContentInJsoupHtml);

        int currentRotation = 1;

        while (currentRotation <= maxPageNum){
            log.info("Parsing page: " + makeNextPageUrl(currentRotation));

            Elements parsedItemElements = argosParser.parseListOfAdElements(pageContentInJsoupHtml);
            List<ParsedItemDto> parsedItemDtos = parsedItemElements.stream().map(element -> argosParser.fetchObject(element)).collect(Collectors.toList());

            itemPriceProcessor.checkAgainstDatabase(parsedItemDtos, storeUrl);

            String nexUrlToScrape = makeNextPageUrl(++currentRotation);
            initFactory(nexUrlToScrape);
        }
    }

    public String makeNextPageUrl(int pageNum) {
        String full = storeUrl.getUrlLink();
        String[] x = full.split("/page:");
        return x[0] + "/page:" + pageNum;
    }

    public void initFactory(String targetUrl) {
        pageContentInJsoupHtml = HttpClient.readContentInJsoupDocument(targetUrl);
    }
}
