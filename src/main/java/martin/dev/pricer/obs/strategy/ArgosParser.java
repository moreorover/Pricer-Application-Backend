package martin.dev.pricer.obs.strategy;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class ArgosParser implements Parser {

    public final String NAME = "Argos";
    public final String PREFIX = "A_";
    public final String BASE_URL = "https://www.argos.co.uk";

    @Override
    public String makeNextPageUrl(String url, int pageNum) {
        String[] x = url.split("/page:");
        return x[0] + "/page:" + pageNum;
    }

    @Override
    public Elements parseListOfAdElements(Document pageContentInJsoupHtml) {
        return pageContentInJsoupHtml.select("div[class^=ProductCardstyles__Wrapper-]");
    }

    @Override
    public int parseMaxPageNum(Document pageContentInJsoupHtml) {
        Element searchResultsCount = pageContentInJsoupHtml.selectFirst("div[class*=search-results-count]");
        String countString = searchResultsCount.attr("data-search-results");
        int adsCount = Integer.parseInt(countString);
        int maxPageNum = (adsCount + 30 - 1) / 30;
        log.info("Found " + adsCount + "ads to scrape, a total of " + maxPageNum + " pages.");
        return maxPageNum;
    }

    @Override
    public String parseTitle(Element adInJsoupHtml) {
        Element titleElement = adInJsoupHtml.selectFirst("a[class*=Title]");
        return titleElement.text();
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) {
        return PREFIX + adInJsoupHtml.attr("data-product-id");
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
        return BASE_URL + urlElement.attr("href");
    }

    @Override
    public String getParserName() {
        return NAME;
    }
}
