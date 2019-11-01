package martin.dev.pricer.scraper.parser.superdrug;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.model.store.StoreUrl;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import martin.dev.pricer.scraper.parser.Scraper;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SuperDrugScraper extends Scraper {

    @Autowired
    private SuperDrugParser superDrugParser;

    @Override
    public void scrapePages(StoreUrl storeUrl) {
        setStoreUrl(storeUrl);
        initFactory(storeUrl.getUrlLink());
        int maxPageNum = superDrugParser.parseMaxPageNum(getPageContentInJsoupHtml());

        int currentRotation = 0;

        while (currentRotation < maxPageNum){
            log.info("Parsing page: " + makeNextPageUrl(currentRotation));

            Elements parsedItemElements = superDrugParser.parseListOfAdElements(getPageContentInJsoupHtml());
            List<ParsedItemDto> parsedItemDtos = parsedItemElements.stream().map(element -> superDrugParser.fetchItemDtoFromHtml(element)).collect(Collectors.toList());

            getItemPriceProcessor().checkAgainstDatabase(parsedItemDtos, storeUrl);

            String nexUrlToScrape = makeNextPageUrl(++currentRotation);
            initFactory(nexUrlToScrape);
        }
    }

    @Override
    public String makeNextPageUrl(int pageNum) {
        String full = getStoreUrl().getUrlLink();
        String[] x = full.split("&page=0");
        return x[0] + "&page=0" + pageNum + "&resultsForPage=60&sort=bestBiz";
    }
}
