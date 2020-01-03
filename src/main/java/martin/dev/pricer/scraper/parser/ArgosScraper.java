package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.fabric.product.DealProcessor;
import martin.dev.pricer.data.model.store.StoreUrl;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class ArgosScraper extends Scraper {

    private DealProcessor dealProcessor;

    public ArgosScraper(StoreUrl storeUrl, DealProcessor dealProcessor) {
        super(storeUrl);
        this.dealProcessor = dealProcessor;
    }

    @Override
    public void scrapePages(StoreUrl storeUrl) {

        int maxPageNum = parseMaxPageNum(super.getPageContentInJsoupHtml());

        int currentRotation = 1;

        while (currentRotation <= maxPageNum) {
            log.info("Parsing page: " + makeNextPageUrl(currentRotation));

            Elements parsedItemElements = parseListOfAdElements(getPageContentInJsoupHtml());
            super.htmlToParsedDtos(parsedItemElements);

            super.getParsedItemDtos().forEach(parsedItemDto -> this.dealProcessor.workOnData(parsedItemDto, storeUrl));

            String nexUrlToScrape = makeNextPageUrl(++currentRotation);
            super.fetchUrlContents(nexUrlToScrape);
        }
    }

    @Override
    public String makeNextPageUrl(int pageNum) {
        String full = getStoreUrl().getUrlLink();
        String[] x = full.split("/page:");
        return x[0] + "/page:" + pageNum;
    }

    @Override
    public Elements parseListOfAdElements(Document pageContentInJsoupHtml) {
        Elements elements = pageContentInJsoupHtml.select("div[class^=ProductCardstyles__Wrapper-]");
        super.validateElements(elements);
        return elements;
    }

    @Override
    public int parseMaxPageNum(Document pageContentInJsoupHtml) {
        Element searchResultsCount = pageContentInJsoupHtml.selectFirst("div[class*=search-results-count]");
        String countString = searchResultsCount.attr("data-search-results");
        int count = Integer.parseInt(countString);
        return (count + 30 - 1) / 30;
    }

    @Override
    public String parseTitle(Element adInJsoupHtml) {
        Element titleElement = adInJsoupHtml.selectFirst("a[class*=Title]");
        return titleElement.text();
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) {
        return "A_" + adInJsoupHtml.attr("data-product-id");
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) {
        String priceString = adInJsoupHtml.selectFirst("div[class*=PriceText]").text();
        priceString = priceString.replaceAll("[^\\d.]", "");
        return Double.parseDouble(priceString);
    }

    @Override
    public String parseImage(Element adInJsoupHtml) {
        Element imgElement = adInJsoupHtml.selectFirst("div[class*=ImageWrapper]");
        imgElement = imgElement.selectFirst("picture");
        imgElement = imgElement.selectFirst("img");
        return imgElement.attr("src");
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) {
        Element urlElement = adInJsoupHtml.selectFirst("a");
        String urlBase = "https://www.argos.co.uk";
        return urlBase + urlElement.attr("href");
    }
}
