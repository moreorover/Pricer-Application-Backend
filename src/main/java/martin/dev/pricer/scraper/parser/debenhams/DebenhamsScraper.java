package martin.dev.pricer.scraper.parser.debenhams;

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
public class DebenhamsScraper extends Scraper {

    @Autowired
    private DebenhamsParser debenhamsParser;

    @Override
    public void scrapePages(StoreUrl storeUrl) {
        setStoreUrl(storeUrl);
        initFactory(storeUrl.getUrlLink());
        int maxPageNum = debenhamsParser.parseMaxPageNum(getPageContentInJsoupHtml());

        int currentRotation = 1;

        while (currentRotation <= maxPageNum){
            log.info("Parsing page: " + makeNextPageUrl(currentRotation));

            Elements parsedItemElements = debenhamsParser.parseListOfAdElements(getPageContentInJsoupHtml());
            List<ParsedItemDto> parsedItemDtos = parsedItemElements.stream().map(element -> debenhamsParser.fetchItemDtoFromHtml(element)).collect(Collectors.toList());

            getItemPriceProcessor().checkAgainstDatabase(parsedItemDtos, storeUrl);

            String nexUrlToScrape = makeNextPageUrl(++currentRotation);
            initFactory(nexUrlToScrape);
        }
    }

    @Override
    public String makeNextPageUrl(int pageNum) {
        String full = getStoreUrl().getUrlLink();
        String[] x = full.split("pn=");
        return x[0] + "pn=" + pageNum + "&?shipToCntry=GB";
    }
}
