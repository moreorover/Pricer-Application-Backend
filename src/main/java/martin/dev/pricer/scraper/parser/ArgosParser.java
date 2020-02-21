package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.fabric.product.DealProcessor;
import martin.dev.pricer.data.model.store.StoreUrl;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class ArgosParser implements ParserMongo {

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
