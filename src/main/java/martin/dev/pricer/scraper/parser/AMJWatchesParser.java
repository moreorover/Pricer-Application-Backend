package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class AMJWatchesParser implements Parser {

    @Override
    public String makeNextPageUrl(String url, int pageNum) {
        String[] x = url.split("page=");
        return x[0] + "page=" + pageNum;
    }

    @Override
    public Elements parseListOfAdElements(Document pageContentInJsoupHtml) {
        return pageContentInJsoupHtml.select("div[class=watch-sec]");
    }

    @Override
    public int parseMaxPageNum(Document pageContentInJsoupHtml) {
        Element productsCountElement = pageContentInJsoupHtml.selectFirst("div[class=items-on-page]");
        String countText = productsCountElement.children().last().text();
        countText = countText.replaceAll("[^\\d.]", "");
        int adsCount = Integer.parseInt(countText);
        int maxPageNum = (adsCount + 40 - 1) / 40;
        log.info("Found " + adsCount + "ads to scrape, a total of " + maxPageNum + " pages.");
        return maxPageNum;
    }

    @Override
    public String parseTitle(Element adInJsoupHtml) {
        Element imgElement = adInJsoupHtml.selectFirst("div[class=watch-image]");
        imgElement = imgElement.selectFirst("a");
        return imgElement.attr("title");
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) {
        try {
            Element imgElement = adInJsoupHtml.selectFirst("div[class=watch-image]");
            imgElement = imgElement.selectFirst("a");
            String url = imgElement.attr("href");
            url = url.split(".uk/")[1];
            return "AMJW_" + url;
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) {
        Element detailsElement = adInJsoupHtml.selectFirst("div[class=watch-details]");

        String priceString = detailsElement.children().last().text().replaceAll("[^\\d.]", "");
        return Double.parseDouble(priceString);
    }

    @Override
    public String parseImage(Element adInJsoupHtml) {
        Element imgElement = adInJsoupHtml.selectFirst("div[class=watch-image]");
        imgElement = imgElement.selectFirst("a");
        imgElement = imgElement.selectFirst("img");
        return imgElement.attr("data-src");
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) {
        Element imgElement = adInJsoupHtml.selectFirst("div[class=watch-image]");
        imgElement = imgElement.selectFirst("a");
        return imgElement.attr("href");
    }
}
