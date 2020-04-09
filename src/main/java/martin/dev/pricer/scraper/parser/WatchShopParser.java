package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.Parser;
import martin.dev.pricer.scraper.ParserException;
import martin.dev.pricer.scraper.ParserValidator;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class WatchShopParser extends Parser {

    public WatchShopParser() {
        super("Watch Shop", "WS_", "https://www.watchshop.com", 192);
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

        Elements parsedElements = pageContentInJsoupHtml.select("div[class*=product-container]");
        ParserValidator.validateElements(parsedElements, this);

        return parsedElements;
    }

    @Override
    public int parseMaxPageNum(Document pageContentInJsoupHtml) throws ParserException {
        setState("parseMaxPageNum");

        Elements showResults = pageContentInJsoupHtml.select("div[class=show-results]");
        ParserValidator.validateElements(showResults, this);
        String results = showResults.text();
        ParserValidator.validateStringIsNotEmpty(results, this);
        String[] resultsArray = results.split(" of ");
        ParserValidator.validateStringArray(resultsArray, 2, this);
        String result = resultsArray[1];
        ParserValidator.validateStringIsNotEmpty(result, this);
        Integer adsCount = parseIntegerFromString(result);
        ParserValidator.validatePositiveInteger(adsCount, this);
        Integer maxPageNum = calculateTotalPages(adsCount);

        // TODO take logging outside of the parser
        log.info("Found " + adsCount + " ads to scrape, a total of " + maxPageNum + " pages.");
        return maxPageNum;
    }

    @Override
    public String parseTitle(Element adInJsoupHtml) throws ParserException {
        setState("parseTitle");

        Element titleElement = adInJsoupHtml.selectFirst("meta[itemprop=name]");
        ParserValidator.validateElement(titleElement, this, adInJsoupHtml);
        String title = titleElement.attr("content").trim();
        ParserValidator.validateStringIsNotEmpty(title, this);

        return title;
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) throws ParserException {
        setState("parseUpc");

        Element upcElement = adInJsoupHtml.selectFirst("meta[itemprop=sku]");
        ParserValidator.validateElement(upcElement, this, adInJsoupHtml);
        String upc = upcElement.attr("content");
        ParserValidator.validateStringIsNotEmpty(upc, this);

        return getPREFIX() + upc;
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) throws ParserException {
        setState("parsePrice");

        Element priceElement = adInJsoupHtml.selectFirst("div[class=product-price]");
        ParserValidator.validateElement(priceElement, this, adInJsoupHtml);
        priceElement = priceElement.selectFirst("strong");
        ParserValidator.validateElement(priceElement, this, adInJsoupHtml);
        String priceString = priceElement.text();
        ParserValidator.validateStringIsNotEmpty(priceString, this);
        Double price = parseDoubleFromString(priceString);
        ParserValidator.validatePositiveDouble(price, this);

        return price;
    }

    @Override
    public String parseImage(Element adInJsoupHtml) throws ParserException {
        setState("parseImage");


        Element imgElement = adInJsoupHtml.selectFirst("div[class=product-img]");
        ParserValidator.validateElement(imgElement, this, adInJsoupHtml);
        imgElement = imgElement.selectFirst("img");
        ParserValidator.validateElement(imgElement, this, adInJsoupHtml);
        String imgSrc = imgElement.attr("src");
        ParserValidator.validateStringIsNotEmpty(imgSrc, this);
        if (imgSrc.endsWith("loader_border.gif")) {
            return "";
        }
        return imgSrc;
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) throws ParserException {
        setState("parseUrl");

        Element imgElement = adInJsoupHtml.selectFirst("div[class=product-img]");
        ParserValidator.validateElement(imgElement, this, adInJsoupHtml);
        imgElement = imgElement.selectFirst("a");
        ParserValidator.validateElement(imgElement, this, adInJsoupHtml);
        String url = imgElement.attr("href");
        ParserValidator.validateStringIsNotEmpty(url, this);

        return getBASE_URL() + url;
    }
}
