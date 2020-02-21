package martin.dev.pricer.scraper.parser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CreationWatchesParser implements ParserMongo {

    @Override
    public String makeNextPageUrl(String url, int pageNum) {
        String[] x = url.split("/index-");
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
