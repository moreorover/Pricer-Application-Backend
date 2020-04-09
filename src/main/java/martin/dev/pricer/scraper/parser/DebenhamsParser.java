package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.Parser;
import martin.dev.pricer.scraper.ParserException;
import martin.dev.pricer.scraper.ParserValidator;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class DebenhamsParser extends Parser {

    public DebenhamsParser() {
        super("Debenhams", "DBH_", "https://www.debenhams.com", 60);
    }

    @Override
    public String makeNextPageUrl(String url, int pageNum) {
        setState("makeNextPageUrl");

        String[] x = url.split("pn=");
        return x[0] + "pn=" + pageNum + "&?shipToCntry=GB";
    }

    @Override
    public Elements parseListOfAdElements(Document pageContentInJsoupHtml) throws ParserException {
        setState("parseListOfAdElements");

        Elements parsedElements = pageContentInJsoupHtml.select("div[class^=c-product-item]");
        ParserValidator.validateElements(parsedElements, this);

        return parsedElements;
    }

    @Override
    public int parseMaxPageNum(Document pageContentInJsoupHtml) throws ParserException {
        setState("parseMaxPageNum");

        Element paginationElement = pageContentInJsoupHtml.selectFirst("div[class*=dbh-count]");
        ParserValidator.validateElement(paginationElement, this);
        String countString = paginationElement.text();
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

        Element titleElement = adInJsoupHtml.selectFirst("h2[class^=c-product-item-title]");
        ParserValidator.validateElement(titleElement, this, adInJsoupHtml);
        String title = titleElement.text();
        ParserValidator.validateStringIsNotEmpty(title, this);

        return title;
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) throws ParserException {
        setState("parseUpc");

        Element aElement = adInJsoupHtml.selectFirst("a");
        ParserValidator.validateElement(aElement, this, adInJsoupHtml);
        String upcText = aElement.attr("href");
        ParserValidator.validateStringIsNotEmpty(upcText, this);
        String[] upcTextArray = upcText.split("prod_");
        ParserValidator.validateStringArray(upcTextArray, 2, this, adInJsoupHtml);
        String upc = upcTextArray[1];
        ParserValidator.validateStringIsNotEmpty(upc, this);

        return getPREFIX() + upc;
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) throws ParserException {
        setState("parsePrice");

        Element priceElement = adInJsoupHtml.selectFirst("span[itemprop=price]");
        ParserValidator.validateElement(priceElement, this, adInJsoupHtml);
        String priceString = priceElement.text();
        ParserValidator.validateStringIsNotEmpty(priceString, this);
        Double price = parseDoubleFromString(priceString);
        ParserValidator.validatePositiveDouble(price, this);

        return price;
    }

    @Override
    public String parseImage(Element adInJsoupHtml) {
        setState("parseImage");
        // Not parsing images as these are loaded dynamically with JSS when scrolling.
        return "";
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) throws ParserException {
        setState("parseUrl");

        Element aElement = adInJsoupHtml.selectFirst("a");
        ParserValidator.validateElement(aElement, this, adInJsoupHtml);
        String url = aElement.attr("href");
        ParserValidator.validateStringIsNotEmpty(url, this);

        return getBASE_URL() + url;
    }
}
