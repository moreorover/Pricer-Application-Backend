package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.model.store.StoreUrl;
import martin.dev.pricer.scraper.client.HttpClient;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public abstract class Scraper implements Parser {

    private List<ParsedItemDto> parsedItemDtos;
    private StoreUrl storeUrl;
    private Document pageContentInJsoupHtml;

    public Scraper(StoreUrl storeUrl) {
        this.storeUrl = storeUrl;
        this.fetchUrlContents(storeUrl.getUrlLink());
    }

    public StoreUrl getStoreUrl() {
        return storeUrl;
    }

    public Document getPageContentInJsoupHtml() {
        return pageContentInJsoupHtml;
    }

    public abstract void scrapePages(StoreUrl storeUrl);

    public abstract String makeNextPageUrl(int pageNum);

    public void fetchUrlContents(String targetUrl) {
        this.pageContentInJsoupHtml = HttpClient.readContentInJsoupDocument(targetUrl);
    }

    public void htmlToParsedDtos(Elements parsedItemElements) {
        if (this.parsedItemDtos != null) this.parsedItemDtos.clear();

        this.parsedItemDtos = parsedItemElements
                .stream()
                .map(element -> new ParsedItemDto(
                        parseTitle(element),
                        parseUrl(element),
                        parseImage(element),
                        parseUpc(element),
                        parsePrice(element)
                ))
                .filter(ParsedItemDto::isValid)
                .collect(Collectors.toList());

        log.info("Successfully parsed " + this.parsedItemDtos.size() + " Ads");
    }

    public List<ParsedItemDto> getParsedItemDtos() {
        return parsedItemDtos;
    }

    public void validateElements(Elements elements) {
        if (elements.size() > 0){
            log.info("Found " + elements.size() + " Ad elements");
            return;
        }
        log.info("Elements set found to be empty, check parsing logic");
    }
}
