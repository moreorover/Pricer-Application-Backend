package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.ParserI;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class WatchoParser implements ParserI {

    public final String NAME = "Watcho";
    public final String PREFIX = "W_";
    public final String BASE_URL = "https://www.watcho.co.uk";

    @Override
    public String makeNextPageUrl(String url, int pageNum) {
        String[] x = url.split("\\?page=");
        return x[0] + "?page=" + pageNum;
    }

    @Override
    public Elements parseListOfAdElements(Document pageContentInJsoupHtml) {
        return pageContentInJsoupHtml.select("li[class=product]");
    }

    @Override
    public int parseMaxPageNum(Document pageContentInJsoupHtml) {
        Elements paginationElements = pageContentInJsoupHtml.select("li[class^=pagination-item]");
        if (paginationElements.size() == 0) {
            log.info("Found " + "?" + "ads to scrape, a total of " + 1 + " pages.");
            return 1;
        }
        Element lastPaginationElement = paginationElements.get(paginationElements.size() - 2);
        int maxPageNum = Integer.parseInt(lastPaginationElement.text());
        log.info("Found " + "?" + "ads to scrape, a total of " + maxPageNum + " pages.");
        return maxPageNum;
    }

    @Override
    public String parseTitle(Element adInJsoupHtml) {
        Element titleElement = adInJsoupHtml.selectFirst("h4[class=card-title]");
        return titleElement.text();
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) {
        return PREFIX + adInJsoupHtml.selectFirst("article").attr("data-entity-id");
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) {
        String priceString = adInJsoupHtml.selectFirst("span[class=price price--withTax]").text();
        priceString = priceString.replaceAll("[^\\d.]", "");
        return Double.parseDouble(priceString);
    }

    @Override
    public String parseImage(Element adInJsoupHtml) {
        Element imgElement = adInJsoupHtml.selectFirst("img[class^=card-image]");
        return imgElement.attr("data-src");
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) {
        Element urlElement = adInJsoupHtml.selectFirst("a");
        return urlElement.attr("href");
    }

    @Override
    public String getParserName() {
        return NAME;
    }
}
