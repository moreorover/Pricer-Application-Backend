package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.ParserI;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class SuperDrugParser implements ParserI {

    public final String NAME = "Superdrug";
    public final String PREFIX = "SD_";
    public final String BASE_URL = "https://www.superdrug.com";

    @Override
    public String makeNextPageUrl(String url, int pageNum) {
        String[] x = url.split("&page=0");
        return x[0] + "&page=0" + pageNum + "&resultsForPage=60&sort=bestBiz";
    }

    @Override
    public Elements parseListOfAdElements(Document pageContentInJsoupHtml) {
        return pageContentInJsoupHtml.select("div[class=item__content]");
    }

    @Override
    public int parseMaxPageNum(Document pageContentInJsoupHtml) {
        Elements paginationElements = pageContentInJsoupHtml.select("ul[class=pagination__list]");
        paginationElements = paginationElements.select("li");
        int countOfPaginationElements = paginationElements.size();
        if (countOfPaginationElements == 0) {
            return 0;
        } else {
            String elementText = paginationElements.get(countOfPaginationElements - 2).text();
            int maxPageNum = Integer.parseInt(elementText);
            log.info("Found " + "?" + "ads to scrape, a total of " + maxPageNum + " pages.");
            return maxPageNum;
        }
    }

    @Override
    public String parseTitle(Element adInJsoupHtml) {
        Element titleElement = adInJsoupHtml.selectFirst("a[class*=item__productName]");
        return titleElement.text();
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) {
        String urlString = parseUrl(adInJsoupHtml);
        String[] urlSplit = urlString.split("/p/");
        return PREFIX + urlSplit[1];
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) {
        String priceString = adInJsoupHtml.selectFirst("span[class*=item__price--now]").text();
        priceString = priceString.replaceAll("[^\\d.]", "");
        try {
            return Double.parseDouble(priceString);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    @Override
    public String parseImage(Element adInJsoupHtml) {
        Element imgElement = adInJsoupHtml.selectFirst("img");
        if (!imgElement.attr("src").equals("")) {
            return BASE_URL + "/" + imgElement.attr("src");
        } else {
            return BASE_URL + "/" + imgElement.attr("data-src");
        }
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) {
        Element titleElement = adInJsoupHtml.selectFirst("a[class*=item__productName]");
        return BASE_URL + "/" + titleElement.attr("href");
    }

    @Override
    public String getParserName() {
        return NAME;
    }
}
