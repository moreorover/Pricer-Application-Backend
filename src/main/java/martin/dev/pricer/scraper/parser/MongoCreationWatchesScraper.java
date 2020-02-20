package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.model.mongo.model.Item;
import martin.dev.pricer.data.model.mongo.model.Price;
import martin.dev.pricer.data.model.mongo.model.Store;
import martin.dev.pricer.data.model.mongo.model.Url;
import martin.dev.pricer.data.model.mongo.service.ItemService;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class MongoCreationWatchesScraper<T extends ItemService> extends MongoScraper<ItemService> {

    public MongoCreationWatchesScraper(T itemService, Store store, Url url) {
        super(itemService, store, url);
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
                            getUrl());

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
            super.fetchUrlContents(nexUrlToScrape);
        }
    }

    @Override
    public String makeNextPageUrl(int pageNum) {
//        https://www.creationwatches.com/products/seiko-75/index-1-5d.html?currency=GBP
//        https://www.creationwatches.com/products/tissot-247/index-1-5d.html?currency=GBP
//        tissot-watches-209
        String full = getUrl().getUrl();
        String[] x = full.split("/index-");
        return x[0] + "/index-" + pageNum + "-5d.html?currency=GBP";
    }

    @Override
    public Elements parseListOfAdElements(Document pageContentInJsoupHtml) {
        return pageContentInJsoupHtml.select("div[class=product-box]");
    }

    @Override
    public int parseMaxPageNum(Document pageContentInJsoupHtml) {
        Element countBox = pageContentInJsoupHtml.selectFirst("div[class=display-heading-box]").selectFirst("strong");
        String countString = countBox.text().split("of")[1];
        countString = countString.replaceAll("[^\\d.]", "");
        int adsCount = Integer.parseInt(countString);
        return (adsCount + 60 - 1) / 60;
    }

    @Override
    public String parseTitle(Element adInJsoupHtml) {
        Element titleElement = adInJsoupHtml.selectFirst("h3[class=product-name]").selectFirst("a");
        return titleElement.text();
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) {
        Element modelElement = adInJsoupHtml.selectFirst("p[class=product-model-no]");
        return "CW_" + modelElement.text().split(": ")[1];
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) {
        Element titleElement = adInJsoupHtml.selectFirst("p[class=product-price]").selectFirst("span");
        String priceString = titleElement.text().replaceAll("[^\\d.]", "");
        return Double.parseDouble(priceString);
    }

    @Override
    public String parseImage(Element adInJsoupHtml) {
        Element titleElement = adInJsoupHtml.selectFirst("div[class=product-img-box]").selectFirst("img");
        return titleElement.attr("src");
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) {
        Element titleElement = adInJsoupHtml.selectFirst("h3[class=product-name]").selectFirst("a");
        return titleElement.attr("href");
    }
}
