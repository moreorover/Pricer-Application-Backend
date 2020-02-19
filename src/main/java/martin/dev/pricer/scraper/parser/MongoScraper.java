package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.model.mongo.model.Store;
import martin.dev.pricer.data.model.mongo.model.Url;
import martin.dev.pricer.data.model.mongo.repository.MongoItemRepository;
import martin.dev.pricer.scraper.client.HttpClient;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public abstract class MongoScraper implements Parser {

    private MongoItemRepository mongoItemRepository;
    private List<ParsedItemDto> parsedItemDtos;
    private Store store;
    private Url url;
    private Document pageContentInJsoupHtml;

    public MongoScraper(MongoItemRepository mongoItemRepository, Store store, Url url) {
        this.mongoItemRepository = mongoItemRepository;
        this.store = store;
        this.url = url;
        this.fetchUrlContents(url.getUrl());
    }

    public MongoItemRepository getMongoItemRepository() {
        return mongoItemRepository;
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

    public abstract void scrapePages();

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
}