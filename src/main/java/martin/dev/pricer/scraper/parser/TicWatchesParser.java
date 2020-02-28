package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class TicWatchesParser implements Parser {

    @Override
    public String makeNextPageUrl(String url, int pageNum) {
        String[] x = url.split("page=");
        return x[0] + "page=" + pageNum;
    }

    @Override
    public Elements parseListOfAdElements(Document pageContentInJsoupHtml) {
        return pageContentInJsoupHtml.select("div[class^=product]");
    }

    @Override
    public int parseMaxPageNum(Document pageContentInJsoupHtml) {
        Element paginationBlockElement = pageContentInJsoupHtml.selectFirst("div[class=product-listings__top__view-all]");
        String totalResults = paginationBlockElement.text().replaceAll("[^\\d.]", "");
        int adsCount = Integer.parseInt(totalResults);
        int maxPageNum = (adsCount + 24 - 1) / 24;
        log.info("Found " + adsCount + "ads to scrape, a total of " + maxPageNum + " pages.");
        return maxPageNum;
    }

    @Override
    public String parseTitle(Element adInJsoupHtml) {
        return adInJsoupHtml.selectFirst("a").attr("title");
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) {
        String upc = adInJsoupHtml.attr("data-infid");
        return "TIC_" + upc;
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) {
        String priceString = adInJsoupHtml.select("span[class=product-content__price--inc]").text();
        priceString = priceString.replaceAll("[^\\d.]", "");
        return Double.parseDouble(priceString);
    }

    @Override
    public String parseImage(Element adInJsoupHtml) {
        Element imgElement = adInJsoupHtml.selectFirst("img");
        return "https://www.ticwatches.co.uk" + imgElement.attr("src");
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) {
        return adInJsoupHtml.selectFirst("a").attr("href");
    }
}
