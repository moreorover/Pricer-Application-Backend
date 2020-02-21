package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.fabric.product.DealProcessor;
import martin.dev.pricer.data.model.store.StoreUrl;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class WatchShopParser implements ParserMongo {

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
        return (adsCount + 192 - 1) / 192;
    }

    @Override
    public String parseTitle(Element adInJsoupHtml){
        Element titleElement = adInJsoupHtml.selectFirst("meta[itemprop=name]");
        return titleElement.attr("content").trim();
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) {
        return "WS_" + adInJsoupHtml.selectFirst("meta[itemprop=sku]").attr("content");
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
        if (imgSrc.endsWith("loader_border.gif")){
            return "";
        }
        return imgSrc;
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) {
        Element imgElement = adInJsoupHtml.selectFirst("div[class=product-img]");
        imgElement = imgElement.selectFirst("a");
        return "https://www.watchshop.com" + imgElement.attr("href");
    }
}
