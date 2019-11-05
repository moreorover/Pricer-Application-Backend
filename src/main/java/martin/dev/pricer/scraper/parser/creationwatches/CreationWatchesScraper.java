package martin.dev.pricer.scraper.parser.creationwatches;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.model.store.StoreUrl;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import martin.dev.pricer.scraper.parser.Scraper;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CreationWatchesScraper extends Scraper {

    @Autowired
    private CreationWatchesParser creationWatchesParser;

    @Override
    public void scrapePages(StoreUrl storeUrl) {
        setStoreUrl(storeUrl);
        initFactory(storeUrl.getUrlLink());
        int maxPageNum = creationWatchesParser.parseMaxPageNum(getPageContentInJsoupHtml());

        int currentRotation = 1;

        while (currentRotation <= maxPageNum){
            log.info("Parsing page: " + makeNextPageUrl(currentRotation));

            Elements parsedItemElements = creationWatchesParser.parseListOfAdElements(getPageContentInJsoupHtml());
            List<ParsedItemDto> parsedItemDtos = parsedItemElements.stream().map(element -> creationWatchesParser.fetchItemDtoFromHtml(element)).collect(Collectors.toList());

            getItemPriceProcessor().checkAgainstDatabase(parsedItemDtos, storeUrl);

            String nexUrlToScrape = makeNextPageUrl(++currentRotation);
            initFactory(nexUrlToScrape);
        }
    }

    @Override
    public String makeNextPageUrl(int pageNum) {
//        https://www.creationwatches.com/products/seiko-75/index-1-5d.html?currency=GBP
//        https://www.creationwatches.com/products/tissot-247/index-1-5d.html?currency=GBP
//        tissot-watches-209
        String full = getStoreUrl().getUrlLink();
        String[] x = full.split("/index-");
        return x[0] + "/index-" + pageNum + "-5d.html?currency=GBP";
    }
}
