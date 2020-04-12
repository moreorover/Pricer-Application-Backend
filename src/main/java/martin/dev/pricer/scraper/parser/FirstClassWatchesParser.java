package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.Parser;
import martin.dev.pricer.scraper.ParserException;
import martin.dev.pricer.scraper.ParserValidator;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class FirstClassWatchesParser extends Parser {

    public FirstClassWatchesParser() {
        super("First Class Watches", "FCW_", "https://www.firstclasswatches.co.uk", 96);
    }

    @Override
    public String makeNextPageUrl(String url, int pageNum) {
        setState("makeNextPageUrl");

        String[] x = url.split("&page=");
        return x[0] + "&page=" + pageNum;
    }

    @Override
    public Elements parseListOfAdElements(Document pageContentInJsoupHtml) throws ParserException {
        setState("parseListOfAdElements");

        Elements parsedElements = pageContentInJsoupHtml.select("a[class=listingproduct]");
        ParserValidator.validateElements(parsedElements, this);

        return parsedElements;
    }

    @Override
    public int parseMaxPageNum(Document pageContentInJsoupHtml) throws ParserException {
        setState("parseMaxPageNum");

        Elements showResults = pageContentInJsoupHtml.select("span[class=tablet-inline]");
        ParserValidator.validateElements(showResults, this);
        String maxPageNumText = showResults.text();
        ParserValidator.validateStringIsNotEmpty(maxPageNumText, this);
        Integer maxPageNum = parseIntegerFromString(maxPageNumText);
        ParserValidator.validatePositiveInteger(maxPageNum, this);

        log.info("Found " + "?" + " ads to scrape, a total of " + maxPageNum + " pages.");
        return maxPageNum;
    }

    @Override
    public String parseTitle(Element adInJsoupHtml) throws ParserException {
        setState("parseTitle");

        String title = adInJsoupHtml.attr("title").trim();
        ParserValidator.validateStringIsNotEmpty(title, this, adInJsoupHtml);

        return title;
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) throws ParserException {
        setState("parseUpc");

        String upc = adInJsoupHtml.attr("data-id");
        ParserValidator.validateStringIsNotEmpty(upc, this, adInJsoupHtml);

        return getPREFIX() + upc;
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) throws ParserException {
        setState("parsePrice");

        String priceString = adInJsoupHtml.attr("data-price");
        ParserValidator.validateStringIsNotEmpty(priceString, this, adInJsoupHtml);
        Double price = parseDoubleFromString(priceString);
        ParserValidator.validatePositiveDouble(price, this);

        return price;
    }

    @Override
    public String parseImage(Element adInJsoupHtml) throws ParserException {
        setState("parseImage");

        Element imgElement = adInJsoupHtml.selectFirst("div[class=image]").selectFirst("img");
        ParserValidator.validateElement(imgElement, this, adInJsoupHtml);
        String imgUrl = imgElement.attr("src");
        ParserValidator.validateStringIsNotEmpty(imgUrl, this, adInJsoupHtml);
        if (imgUrl.endsWith("loader_border.gif")) {
            return "";
        }
        return imgUrl;
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) throws ParserException {
        setState("parseUrl");

        String url = adInJsoupHtml.attr("href");
        ParserValidator.validateStringIsNotEmpty(url, this, adInJsoupHtml);

        return url;
    }
}
