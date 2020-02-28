package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.model.Store;
import martin.dev.pricer.data.model.Url;
import martin.dev.pricer.data.service.ItemServiceI;
import martin.dev.pricer.scraper.client.HttpClient;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class Scraper<T extends ItemServiceI, R extends Parser> {

    private T itemService;
    private R parser;
    private List<ParsedItemDto> parsedItemDtos;
    private Store store;
    private Url url;
    private Document pageContentInJsoupHtml;
    private int maxPageNum;

    public Scraper(T itemService, R parser, Store store, Url url) {
        this.itemService = itemService;
        this.parser = parser;
        this.store = store;
        this.url = url;
        this.fetchUrlContents(url.getUrl());
        this.maxPageNum = parser.parseMaxPageNum(this.pageContentInJsoupHtml);
    }

    public T getItemService() {
        return itemService;
    }

    public Store getStore() {
        return store;
    }

    public Url getUrl() {
        return url;
    }

    public Document getPageContentInJsoupHtml() {
        return pageContentInJsoupHtml;
    }

    public int getMaxPageNum() {
        return maxPageNum;
    }

    public void scrapePagesFromZero() {
        int currentRotation = 0;

        while (currentRotation < getMaxPageNum()) {
            log.info("Parsing page: " + parser.makeNextPageUrl(url.getUrl(), currentRotation));

            Elements parsedItemElements = parser.parseListOfAdElements(getPageContentInJsoupHtml());
            htmlToParsedDtos(parsedItemElements, parser.makeNextPageUrl(url.getUrl(), currentRotation));

            getParsedItemDtos().forEach(parsedItemDto -> getItemService().processParsedItem(parsedItemDto, getStore(), getUrl()));

            String nexUrlToScrape = parser.makeNextPageUrl(url.getUrl(), ++currentRotation);
            fetchUrlContents(nexUrlToScrape);
        }
    }

    public void scrapePagesFromOne() {
        int currentRotation = 1;

        while (currentRotation <= getMaxPageNum()) {
            log.info("Parsing page: " + parser.makeNextPageUrl(url.getUrl(), currentRotation));

            Elements parsedItemElements = parser.parseListOfAdElements(getPageContentInJsoupHtml());
            htmlToParsedDtos(parsedItemElements, parser.makeNextPageUrl(url.getUrl(), currentRotation));

            getParsedItemDtos().forEach(parsedItemDto -> getItemService().processParsedItem(parsedItemDto, getStore(), getUrl()));

            String nexUrlToScrape = parser.makeNextPageUrl(url.getUrl(), ++currentRotation);
            fetchUrlContents(nexUrlToScrape);
        }
    }

    public void fetchUrlContents(String targetUrl) {
        this.pageContentInJsoupHtml = HttpClient.readContentInJsoupDocument(targetUrl);
    }

    public void htmlToParsedDtos(Elements parsedItemElements, String url) {
        if (this.parsedItemDtos != null) this.parsedItemDtos.clear();

        this.parsedItemDtos = parsedItemElements
                .stream()
                .map(element -> new ParsedItemDto(
                        parser.parseTitle(element),
                        parser.parseUrl(element),
                        parser.parseImage(element),
                        parser.parseUpc(element),
                        parser.parsePrice(element),
                        url
                ))
                .filter(ParsedItemDto::isValid)
                .collect(Collectors.toList());
        if (this.parsedItemDtos.size() == parsedItemElements.size()) {
            log.info("Successfully parsed " + this.parsedItemDtos.size() + " Ads");
        } else {
            log.error("Parsed only " + this.parsedItemDtos.size() + "Ads. Out of total: " + parsedItemElements.size());
        }

    }

    public List<ParsedItemDto> getParsedItemDtos() {
        return parsedItemDtos;
    }

    public void validateElements(Elements elements) {
        if (elements.size() > 0) {
            log.info("Found " + elements.size() + " Ad elements");
            return;
        }
        log.info("Elements set found to be empty, check parsing logic");
    }

    public void logItemCount(int totalItems, int totalPages) {
        log.info("Found " + totalItems + "ads to scrape, a total of " + totalPages + " pages.");
    }
}
