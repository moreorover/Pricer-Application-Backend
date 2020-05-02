package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.Parser;
import martin.dev.pricer.scraper.ParserException;
import martin.dev.pricer.scraper.ParserValidator;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class ArgosParser extends Parser {

    public ArgosParser() {
        super("Argos", "A_", "https://www.argos.co.uk", 30);
    }

    @Override
    public String makeNextPageUrl(String url, int pageNum) {
        setState("makeNextPageUrl");

        if (pageNum == 1) {
            return url;
        }
        String[] x = url.split("page:");
        return x[0] + "page:" + pageNum;
    }

    @Override
    public Elements parseListOfAdElements(Document pageContentInJsoupHtml) throws ParserException {
        setState("parseListOfAdElements");

        Elements parsedElements = pageContentInJsoupHtml.select("div[class^=ProductCardstyles__Wrapper-]");
        ParserValidator.validateElements(parsedElements, this);

        return parsedElements;
    }

    @Override
    public int parseMaxPageNum(Document pageContentInJsoupHtml) throws ParserException {
        setState("parseMaxPageNum");

        Element searchResultsCount = pageContentInJsoupHtml.selectFirst("div[class*=search-results-count]");
        ParserValidator.validateElement(searchResultsCount, this);
        String countString = searchResultsCount.attr("data-search-results");
        ParserValidator.validateStringIsNotEmpty(countString, this);
        Integer adsCount = parseIntegerFromString(countString);
        ParserValidator.validatePositiveInteger(adsCount, this);
        Integer maxPageNum = calculateTotalPages(adsCount);
        ParserValidator.validatePositiveInteger(maxPageNum, this);

        // TODO take logging outside of the parser
        log.info("Found " + adsCount + "ads to scrape, a total of " + maxPageNum + " pages.");
        return maxPageNum;
    }

    @Override
    public String parseTitle(Element adInJsoupHtml) throws ParserException {
        setState("parseTitle");

        Element titleElement = adInJsoupHtml.selectFirst("a[class*=Title]");
        ParserValidator.validateElement(titleElement, this, adInJsoupHtml);
        String title = titleElement.text();
        ParserValidator.validateStringIsNotEmpty(title, this, adInJsoupHtml);

        return title;
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) throws ParserException {
        setState("parseUpc");

        String upc = adInJsoupHtml.attr("data-product-id");
        ParserValidator.validateStringIsNotEmpty(upc, this, adInJsoupHtml);

        return getPREFIX() + upc;
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) throws ParserException {
        setState("parsePrice");

        Element priceElement = adInJsoupHtml.selectFirst("div[class*=PriceText]");
        ParserValidator.validateElement(priceElement, this, adInJsoupHtml);
        String priceString = priceElement.text();
        ParserValidator.validateStringIsNotEmpty(priceString, this, adInJsoupHtml);
        Double price = parseDoubleFromString(priceString);
        ParserValidator.validatePositiveDouble(price, this);

        return price;
    }

    @Override
    public String parseImage(Element adInJsoupHtml) throws ParserException {
        setState("parseImage");

        Element imgElement = adInJsoupHtml.selectFirst("div[class*=ImageWrapper]");
        ParserValidator.validateElement(imgElement, this, adInJsoupHtml);
        imgElement = imgElement.selectFirst("picture");
        ParserValidator.validateElement(imgElement, this, adInJsoupHtml);
        imgElement = imgElement.selectFirst("img");
        ParserValidator.validateElement(imgElement, this, adInJsoupHtml);
        String imgUrl = imgElement.attr("src");
        ParserValidator.validateStringIsNotEmpty(imgUrl, this, adInJsoupHtml);

        return imgUrl;
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) throws ParserException {
        setState("parseUrl");

        Element urlElement = adInJsoupHtml.selectFirst("a");
        ParserValidator.validateElement(urlElement, this, adInJsoupHtml);
        String url = urlElement.attr("href");
        ParserValidator.validateStringIsNotEmpty(url, this, adInJsoupHtml);

        return getBASE_URL() + url;
    }
}
