package martin.dev.pricer.scraper.parser.argos;

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
public class ArgosScraper extends Scraper {

    @Autowired
    private ArgosParser argosParser;

    @Override
    public void scrapePages(StoreUrl storeUrl) {
        setStoreUrl(storeUrl);
        initFactory(storeUrl.getUrlLink());
        int maxPageNum = argosParser.parseMaxPageNum(getPageContentInJsoupHtml());

        int currentRotation = 1;

        while (currentRotation <= maxPageNum){
            log.info("Parsing page: " + makeNextPageUrl(currentRotation));

            Elements parsedItemElements = argosParser.parseListOfAdElements(getPageContentInJsoupHtml());
            List<ParsedItemDto> parsedItemDtos = parsedItemElements.stream().map(element -> argosParser.fetchItemDtoFromHtml(element)).collect(Collectors.toList());

            parsedItemDtos.forEach(parsedItemDto -> this.getDealProcessor().workOnData(parsedItemDto, storeUrl));

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
}
