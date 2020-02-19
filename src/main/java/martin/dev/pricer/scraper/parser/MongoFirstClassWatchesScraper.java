package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.fabric.product.DealProcessor;
import martin.dev.pricer.data.model.mongo.model.Item;
import martin.dev.pricer.data.model.mongo.model.Price;
import martin.dev.pricer.data.model.mongo.model.Store;
import martin.dev.pricer.data.model.mongo.model.Url;
import martin.dev.pricer.data.model.mongo.repository.MongoItemRepository;
import martin.dev.pricer.data.model.store.StoreUrl;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class MongoFirstClassWatchesScraper extends MongoScraper {

    public MongoFirstClassWatchesScraper(MongoItemRepository mongoItemRepository, Store store, Url url) {
        super(mongoItemRepository, store, url);
    }

    @Override
    public void scrapePages() {
        int maxPageNum = parseMaxPageNum(super.getPageContentInJsoupHtml());

        int currentRotation = 1;

        while (currentRotation <= maxPageNum) {
            log.info("Parsing page: " + makeNextPageUrl(currentRotation));

            Elements parsedItemElements = parseListOfAdElements(super.getPageContentInJsoupHtml());
            super.htmlToParsedDtos(parsedItemElements);

//            super.getParsedItemDtos().forEach(parsedItemDto -> this.dealProcessor.workOnData(parsedItemDto, super.getStoreUrl()));

            super.getParsedItemDtos().forEach(parsedItemDto -> {

                Item dbItem = getMongoItemRepository().findByUpc(parsedItemDto.getUpc());

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
                            getStore());

                    getMongoItemRepository().save(newItem);
                }
            });

            String nexUrlToScrape = makeNextPageUrl(++currentRotation);
            super.fetchUrlContents(nexUrlToScrape);
        }
    }

    @Override
    public String makeNextPageUrl(int pageNum) {
        String full = getUrl().getUrl();
        String[] x = full.split("&page=");
        return x[0] + "&page=" + pageNum;
    }

    @Override
    public Elements parseListOfAdElements(Document pageContentInJsoupHtml) {
        return pageContentInJsoupHtml.select("a[class=listingproduct]");
    }

    @Override
    public int parseMaxPageNum(Document pageContentInJsoupHtml) {
        Elements showResults = pageContentInJsoupHtml.select("span[class=tablet-inline]");
        String text = showResults.text().replaceAll("[^\\d.]", "");
        return Integer.parseInt(text);
    }

    @Override
    public String parseTitle(Element adInJsoupHtml){
        return adInJsoupHtml.attr("title").trim();
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) {
        return "FCW_" + adInJsoupHtml.attr("data-id");
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) {
        String priceString = adInJsoupHtml.attr("data-price");
        return Double.parseDouble(priceString);
    }

    @Override
    public String parseImage(Element adInJsoupHtml) {
        Element imgElement = adInJsoupHtml.selectFirst("div[class=image]");
        imgElement = imgElement.selectFirst("img");
        String imgSrc = imgElement.attr("src");
        if (imgSrc.endsWith("loader_border.gif")){
            return "";
        }
        return imgSrc;
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) {
        return adInJsoupHtml.attr("href");
    }
}
