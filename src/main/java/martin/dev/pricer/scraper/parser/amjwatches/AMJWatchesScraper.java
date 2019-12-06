package martin.dev.pricer.scraper.parser.amjwatches;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.model.store.StoreUrl;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import martin.dev.pricer.scraper.parser.Scraper;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AMJWatchesScraper extends Scraper {

    @Autowired
    private AMJWatchesParser amjWatchesParser;

    @Override
    public void scrapePages(StoreUrl storeUrl) {
        setStoreUrl(storeUrl);
        initFactory(storeUrl.getUrlLink());
        int maxPageNum = amjWatchesParser.parseMaxPageNum(getPageContentInJsoupHtml());

        int currentRotation = 1;

        while (currentRotation <= maxPageNum){
            log.info("Parsing page: " + makeNextPageUrl(currentRotation));

            Elements parsedItemElements = amjWatchesParser.parseListOfAdElements(getPageContentInJsoupHtml());
            List<ParsedItemDto> parsedItemDtos = parsedItemElements.stream()
                    .map(element -> amjWatchesParser.fetchItemDtoFromHtml(element))
                    .filter(c -> c.getUpc() != null)
                    .collect(Collectors.toList());

            parsedItemDtos.forEach(parsedItemDto -> this.getDealProcessor().workOnData(parsedItemDto, storeUrl));

            String nexUrlToScrape = makeNextPageUrl(++currentRotation);
            initFactory(nexUrlToScrape);
        }
    }

    @Override
    public String makeNextPageUrl(int pageNum) {
        String full = getStoreUrl().getUrlLink();
        String[] x = full.split("page=");
        return x[0] + "page=" + pageNum;
    }
}
