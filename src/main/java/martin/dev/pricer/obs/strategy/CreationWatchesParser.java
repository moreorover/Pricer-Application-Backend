package martin.dev.pricer.obs.strategy;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class CreationWatchesParser implements Parser {

    public final String NAME = "Creation Watches";
    public final String PREFIX = "CW_";
    public final String BASE_URL = "https://www.creationwatches.com";

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
        int maxPageNum = (adsCount + 60 - 1) / 60;
        log.info("Found " + adsCount + "ads to scrape, a total of " + maxPageNum + " pages.");
        return maxPageNum;
    }

    @Override
    public String parseTitle(Element adInJsoupHtml) {
        Element titleElement = adInJsoupHtml.selectFirst("h3[class=product-name]").selectFirst("a");
        return titleElement.text();
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) {
        Element modelElement = adInJsoupHtml.selectFirst("p[class=product-model-no]");
        return PREFIX + modelElement.text().split(": ")[1];
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

    @Override
    public String getParserName() {
        return NAME;
    }


}
