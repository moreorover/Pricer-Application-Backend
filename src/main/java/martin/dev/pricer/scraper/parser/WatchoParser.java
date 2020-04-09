package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.Parser;
import martin.dev.pricer.scraper.ParserException;
import martin.dev.pricer.scraper.ParserValidator;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class WatchoParser extends Parser {

    public WatchoParser() {
        super("Watcho", "W_", "https://www.watcho.co.uk", 24);
    }

    @Override
    public String makeNextPageUrl(String url, int pageNum) {
        setState("makeNextPageUrl");

        String[] x = url.split("\\?page=");
        return x[0] + "?page=" + pageNum;
    }

    @Override
    public Elements parseListOfAdElements(Document pageContentInJsoupHtml) throws ParserException {
        setState("parseListOfAdElements");

        Elements parsedElements = pageContentInJsoupHtml.select("li[class=product]");
        ParserValidator.validateElements(parsedElements, this);

        return parsedElements;
    }

    @Override
    public int parseMaxPageNum(Document pageContentInJsoupHtml) throws ParserException {
        setState("parseMaxPageNum");

        Elements paginationElements = pageContentInJsoupHtml.select("li[class^=pagination-item]");
        ParserValidator.validateElementsNotNull(paginationElements, this);
        if (paginationElements.size() == 0) {
            log.info("Found " + "?" + " ads to scrape, a total of " + 1 + " pages.");
            return 1;
        }
        Element lastPaginationElement = paginationElements.get(paginationElements.size() - 2);
        ParserValidator.validateElement(lastPaginationElement, this);
        String maxPageNumText = lastPaginationElement.text();
        ParserValidator.validateStringIsNotEmpty(maxPageNumText, this);
        Integer maxPageNum = parseIntegerFromString(maxPageNumText);
        ParserValidator.validatePositiveInteger(maxPageNum, this);

        // TODO take logging outside of the parser
        log.info("Found " + "?" + " ads to scrape, a total of " + maxPageNum + " pages.");
        return maxPageNum;
    }

    @Override
    public String parseTitle(Element adInJsoupHtml) throws ParserException {
        setState("parseTitle");

        Element titleElement = adInJsoupHtml.selectFirst("h4[class=card-title]");
        ParserValidator.validateElement(titleElement, this, adInJsoupHtml);
        String title = titleElement.text();
        ParserValidator.validateStringIsNotEmpty(title, this);

        return title;
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) throws ParserException {
        setState("parseUpc");

        Element upcElement = adInJsoupHtml.selectFirst("article");
        ParserValidator.validateElement(upcElement, this, adInJsoupHtml);
        String upc = upcElement.attr("data-entity-id");
        ParserValidator.validateStringIsNotEmpty(upc, this);

        return upc;
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) throws ParserException {
        setState("parsePrice");

        Element priceElement = adInJsoupHtml.selectFirst("span[class=price price--withTax]");
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

        Element imgElement = adInJsoupHtml.selectFirst("img[class^=card-image]");
        ParserValidator.validateElement(imgElement, this, adInJsoupHtml);
        String imgUrl = imgElement.attr("data-src");
        ParserValidator.validateStringIsNotEmpty(imgUrl, this);

        return imgUrl;
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) throws ParserException {
        setState("parseUrl");

        Element aElement = adInJsoupHtml.selectFirst("a");
        ParserValidator.validateElement(aElement, this, adInJsoupHtml);
        String url = aElement.attr("href");
        ParserValidator.validateStringIsNotEmpty(url, this);

        return url;
    }
}
