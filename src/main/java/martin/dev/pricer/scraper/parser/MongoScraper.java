package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.model.mongo.model.Item;
import martin.dev.pricer.data.model.mongo.model.Price;
import martin.dev.pricer.data.model.mongo.model.Store;
import martin.dev.pricer.data.model.mongo.model.Url;
import martin.dev.pricer.data.model.mongo.service.ItemService;
import martin.dev.pricer.scraper.client.HttpClient;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public abstract class MongoScraper<T extends ItemService> implements Parser {

    private T itemService;
    private List<ParsedItemDto> parsedItemDtos;
    private Store store;
    private Url url;
    private Document pageContentInJsoupHtml;
    private int maxPageNum;

    public MongoScraper(T itemService, Store store, Url url) {
        this.itemService = itemService;
        this.store = store;
        this.url = url;
        this.fetchUrlContents(url.getUrl());
        this.maxPageNum = parseMaxPageNum(this.pageContentInJsoupHtml);
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
            log.info("Parsing page: " + makeNextPageUrl(currentRotation));

            Elements parsedItemElements = parseListOfAdElements(getPageContentInJsoupHtml());
            htmlToParsedDtos(parsedItemElements);

            getParsedItemDtos().forEach(parsedItemDto -> {

                Item dbItem = getItemService().findByUpc(parsedItemDto.getUpc());

                if (dbItem == null){
                    Set<Price> prices = new HashSet<>();
                    prices.add(new Price(parsedItemDto.getPrice(),
                            0.0,
                            LocalDateTime.now()));

                    Item newItem = new Item(parsedItemDto.getUpc(),
                            parsedItemDto.getTitle(),
                            parsedItemDto.getUrl(),
                            parsedItemDto.getImg(),
                            prices,
                            getUrl().getCategories(),
                            getStore(),
                            getUrl().getUrl());

                    getItemService().save(newItem);
                } else if (dbItem.getLastPrice() != parsedItemDto.getPrice()) {
                    double delta = 100 * ((parsedItemDto.getPrice() - dbItem.getLastPrice()) / dbItem.getLastPrice());
                    DecimalFormat df = new DecimalFormat("#.##");
                    double newDelta = Double.parseDouble(df.format(delta));

                    Price newPrice = new Price(parsedItemDto.getPrice(), newDelta, LocalDateTime.now());
                    dbItem.getPrices().add(newPrice);
                    getItemService().save(dbItem);
                }

            });

            String nexUrlToScrape = makeNextPageUrl(++currentRotation);
            fetchUrlContents(nexUrlToScrape);
        }
    }

    public void scrapePagesFromOne() {
        int currentRotation = 1;

        while (currentRotation <= getMaxPageNum()) {
            log.info("Parsing page: " + makeNextPageUrl(currentRotation));

            Elements parsedItemElements = parseListOfAdElements(getPageContentInJsoupHtml());
            htmlToParsedDtos(parsedItemElements);

            getParsedItemDtos().forEach(parsedItemDto -> {

                Item dbItem = getItemService().findByUpc(parsedItemDto.getUpc());

                if (dbItem == null){
                    Set<Price> prices = new HashSet<>();
                    prices.add(new Price(parsedItemDto.getPrice(),
                            0.0,
                            LocalDateTime.now()));

                    Item newItem = new Item(parsedItemDto.getUpc(),
                            parsedItemDto.getTitle(),
                            parsedItemDto.getUrl(),
                            parsedItemDto.getImg(),
                            prices,
                            getUrl().getCategories(),
                            getStore(),
                            getUrl().getUrl());

                    getItemService().save(newItem);
                } else if (dbItem.getLastPrice() != parsedItemDto.getPrice()) {
                    double delta = 100 * ((parsedItemDto.getPrice() - dbItem.getLastPrice()) / dbItem.getLastPrice());
                    DecimalFormat df = new DecimalFormat("#.##");
                    double newDelta = Double.parseDouble(df.format(delta));

                    Price newPrice = new Price(parsedItemDto.getPrice(), newDelta, LocalDateTime.now());
                    dbItem.getPrices().add(newPrice);
                    getItemService().save(dbItem);
                }

            });

            String nexUrlToScrape = makeNextPageUrl(++currentRotation);
            fetchUrlContents(nexUrlToScrape);
        }
    }

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
                        parsePrice(element),
                        getUrl().getUrl()
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
