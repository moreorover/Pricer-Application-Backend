package martin.dev.pricer.scraper.parser.unactive.allbeauty;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.model.store.StoreUrl;
import martin.dev.pricer.scraper.client.HttpClient;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import martin.dev.pricer.scraper.parser.Scraper;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AllBeautyScraper extends Scraper {

    @Autowired
    private AllBeautyParser allBeautyParser;

    @Override
    public void scrapePages(StoreUrl storeUrl) {
        setStoreUrl(storeUrl);
        initFactory(storeUrl.getUrlLink());
        int maxPageNum = allBeautyParser.parseMaxPageNum(getPageContentInJsoupHtml());

        int currentRotation = 0;

        while (currentRotation < maxPageNum){
            log.info("Parsing page: " + makeNextPageUrl(currentRotation));

            Elements parsedItemElements = allBeautyParser.parseListOfAdElements(getPageContentInJsoupHtml());
            List<ParsedItemDto> parsedItemDtos = parsedItemElements.stream().map(element -> allBeautyParser.fetchItemDtoFromHtml(element)).collect(Collectors.toList());

            getItemPriceProcessor().checkAgainstDatabase(parsedItemDtos, storeUrl);

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

    @Override
    public void initFactory(String targetUrl) {
        setPageContentInJsoupHtml(HttpClient.fetchJSPageContent(targetUrl));
    }
}
