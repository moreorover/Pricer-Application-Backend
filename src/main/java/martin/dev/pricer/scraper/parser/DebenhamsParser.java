package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.Parser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class DebenhamsParser implements Parser {

    public final String NAME = "Debenhams";
    public final String PREFIX = "DBH_";
    public final String BASE_URL = "https://www.debenhams.com";

    @Override
    public String makeNextPageUrl(String url, int pageNum) {
        String[] x = url.split("pn=");
        return x[0] + "pn=" + pageNum + "&?shipToCntry=GB";
    }

    @Override
    public Elements parseListOfAdElements(Document pageContentInJsoupHtml) {
        return pageContentInJsoupHtml.select("article[class^=c-product-item]");
    }

    @Override
    public int parseMaxPageNum(Document pageContentInJsoupHtml) {
        String countString = pageContentInJsoupHtml.selectFirst("div[class*=dbh-count]").text();
        countString = countString.replaceAll("[^\\d.]", "");
        int adsCount = Integer.parseInt(countString);
        int maxPageNum = (adsCount + 60 - 1) / 60;
        log.info("Found " + adsCount + "ads to scrape, a total of " + maxPageNum + " pages.");
        return maxPageNum;
    }

    @Override
    public String parseTitle(Element adInJsoupHtml) {
        Element titleElement = adInJsoupHtml.selectFirst("div[class^=c-product-item-title]");
        return titleElement.text();
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) {
        Element aElement = adInJsoupHtml.selectFirst("a");
        return PREFIX + aElement.attr("href").split("prod_")[1];
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) {
        Element priceElement = adInJsoupHtml.selectFirst("span[itemprop=price]");
        String priceString = priceElement.text().replaceAll("[^\\d.]", "");
        return Double.parseDouble(priceString);
    }

    @Override
    public String parseImage(Element adInJsoupHtml) {
//        Element imgElement = adInJsoupHtml.selectFirst("img");
//        return imgElement.attr("src");
        return "";
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) {
        Element aElement = adInJsoupHtml.selectFirst("a");
        return BASE_URL + aElement.attr("href");
    }

    @Override
    public String getParserName() {
        return NAME;
    }
}
