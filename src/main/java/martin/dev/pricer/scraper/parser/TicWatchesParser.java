package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.ParserI;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class TicWatchesParser implements ParserI {

    public final String NAME = "Tic Watches";
    public final String PREFIX = "TIC_";
    public final String BASE_URL = "https://www.ticwatches.co.uk/";

    @Override
    public String makeNextPageUrl(String url, int pageNum) {
        if (pageNum == 1){
            return url;
        }
        String[] x = url.split("\\?page=");
        return x[0] + "?page=" + pageNum;
    }

    @Override
    public Elements parseListOfAdElements(Document pageContentInJsoupHtml) {
        return pageContentInJsoupHtml.select("li[class^=col]");
    }

    @Override
    public int parseMaxPageNum(Document pageContentInJsoupHtml) {
        Element paginationBlockElement = pageContentInJsoupHtml.selectFirst("div[class=product-listings__top__view-all]");
        String totalResults = paginationBlockElement.text().replaceAll("[^\\d.]", "");
        int adsCount = Integer.parseInt(totalResults);
        int maxPageNum = (adsCount + 24 - 1) / 24;
        log.info("Found " + adsCount + " ads to scrape, a total of " + maxPageNum + " pages.");
        return maxPageNum;
    }

    @Override
    public String parseTitle(Element adInJsoupHtml) {
        return adInJsoupHtml.selectFirst("a").attr("title");
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) {
        String upc = adInJsoupHtml.selectFirst("div").attr("data-infid");
        return PREFIX + upc;
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) {
        String priceString = adInJsoupHtml.selectFirst("span[class=product-content__price--inc]").text();
        priceString = priceString.replaceAll("[^\\d.]", "");
        return Double.parseDouble(priceString);
    }

    @Override
    public String parseImage(Element adInJsoupHtml) {
        Element imgElement = adInJsoupHtml.selectFirst("img");
        return BASE_URL + imgElement.attr("data-src");
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) {
        return adInJsoupHtml.selectFirst("a").attr("href");
    }

    @Override
    public String getParserName() {
        return NAME;
    }
}
