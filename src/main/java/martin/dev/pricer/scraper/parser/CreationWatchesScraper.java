package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.fabric.product.DealProcessor;
import martin.dev.pricer.data.model.store.StoreUrl;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class CreationWatchesScraper extends Scraper {
    private DealProcessor dealProcessor;

    public CreationWatchesScraper(StoreUrl storeUrl, DealProcessor dealProcessor) {
        super(storeUrl);
        this.dealProcessor = dealProcessor;
    }

    @Override
    public void scrapePages() {
        int maxPageNum = parseMaxPageNum(super.getPageContentInJsoupHtml());

        int currentRotation = 1;

        while (currentRotation <= maxPageNum) {
            log.info("Parsing page: " + makeNextPageUrl(currentRotation));

            Elements parsedItemElements = parseListOfAdElements(super.getPageContentInJsoupHtml());
            super.htmlToParsedDtos(parsedItemElements);

            super.getParsedItemDtos().forEach(parsedItemDto -> this.dealProcessor.workOnData(parsedItemDto, super.getStoreUrl()));

            String nexUrlToScrape = makeNextPageUrl(++currentRotation);
            super.fetchUrlContents(nexUrlToScrape);
        }
    }

    @Override
    public String makeNextPageUrl(int pageNum) {
//        https://www.creationwatches.com/products/seiko-75/index-1-5d.html?currency=GBP
//        https://www.creationwatches.com/products/tissot-247/index-1-5d.html?currency=GBP
//        tissot-watches-209
        String full = getStoreUrl().getUrlLink();
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
