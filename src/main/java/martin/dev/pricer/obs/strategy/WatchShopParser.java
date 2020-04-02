package martin.dev.pricer.obs.strategy;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class WatchShopParser implements Parser {

    public final String NAME = "Watch Shop";
    public final String PREFIX = "WS_";
    public final String BASE_URL = "https://www.watchshop.com";

    @Override
    public String makeNextPageUrl(String url, int pageNum) {
//        https://www.watchshop.com/mens-watches.html?show=192&page=1
        String[] x = url.split("&page=");
        return x[0] + "&page=" + pageNum;
    }

    @Override
    public Elements parseListOfAdElements(Document pageContentInJsoupHtml) {
        return pageContentInJsoupHtml.select("div[class*=product-container]");
    }

    @Override
    public int parseMaxPageNum(Document pageContentInJsoupHtml) {
        Elements showResults = pageContentInJsoupHtml.select("div[class=show-results]");
        String text = showResults.text().split(" of ")[1].replaceAll("[^\\d.]", "");
        int adsCount = Integer.parseInt(text);
        int maxPageNum = (adsCount + 192 - 1) / 192;
        log.info("Found " + adsCount + "ads to scrape, a total of " + maxPageNum + " pages.");
        return maxPageNum;
    }

    @Override
    public String parseTitle(Element adInJsoupHtml) {
        Element titleElement = adInJsoupHtml.selectFirst("meta[itemprop=name]");
        return titleElement.attr("content").trim();
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) {
        return PREFIX + adInJsoupHtml.selectFirst("meta[itemprop=sku]").attr("content");
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) {
        String priceString = adInJsoupHtml.selectFirst("div[class=product-price]").selectFirst("strong").text();
        priceString = priceString.replaceAll("[^\\d.]", "");
        return Double.parseDouble(priceString);
    }

    @Override
    public String parseImage(Element adInJsoupHtml) {
        Element imgElement = adInJsoupHtml.selectFirst("div[class=product-img]");
        imgElement = imgElement.selectFirst("img");
        String imgSrc = imgElement.attr("src");
        if (imgSrc.endsWith("loader_border.gif")) {
            return "";
        }
        return imgSrc;
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) {
        Element imgElement = adInJsoupHtml.selectFirst("div[class=product-img]");
        imgElement = imgElement.selectFirst("a");
        return BASE_URL + imgElement.attr("href");
    }

    @Override
    public String getParserName() {
        return NAME;
    }
}
