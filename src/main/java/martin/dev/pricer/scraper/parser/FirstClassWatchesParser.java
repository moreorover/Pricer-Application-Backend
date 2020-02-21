package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.fabric.product.DealProcessor;
import martin.dev.pricer.data.model.store.StoreUrl;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class FirstClassWatchesParser implements ParserMongo {

    @Override
    public String makeNextPageUrl(String url, int pageNum) {
        String[] x = url.split("&page=");
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
