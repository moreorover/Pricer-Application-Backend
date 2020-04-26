package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.Parser;
import martin.dev.pricer.scraper.ParserException;
import martin.dev.pricer.scraper.ParserValidator;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class SimpkinsJewellersParser extends Parser {

    public SimpkinsJewellersParser() {
        super("Simpkins Jewellers", "SJ_", "https://simpkinsjewellers.co.uk", 100);
    }

    @Override
    public String makeNextPageUrl(String url, int pageNum) {
        setState("makeNextPageUrl");

        String[] x = url.split("page=");
        return x[0] + "page=" + pageNum;
    }

    @Override
    public Elements parseListOfAdElements(Document pageContentInJsoupHtml) throws ParserException {
        setState("parseListOfAdElements");

        Elements parsedElements = pageContentInJsoupHtml.select("div.product.clearfix.product-hover");
        ParserValidator.validateElements(parsedElements, this);

        return parsedElements;
    }

    @Override
    public int parseMaxPageNum(Document pageContentInJsoupHtml) throws ParserException {
        setState("parseMaxPageNum");

        Element paginationElement = pageContentInJsoupHtml.selectFirst("div.row.pagination-results");
        ParserValidator.validateElement(paginationElement, this);
        Elements paginationElements = paginationElement.children();
        ParserValidator.validateElements(paginationElements, this);
        Element lastPageElement = paginationElements.get(paginationElements.size() - 1);
        ParserValidator.validateElement(lastPageElement, this);
        String paginationText = lastPageElement.text().split(" \\(")[1];
        ParserValidator.validateStringIsNotEmpty(paginationText, this);
        String lastPageNumber = paginationText.replaceAll("[^\\d.]", "");
        ParserValidator.validateStringIsNotEmpty(lastPageNumber, this);
        int maxPageNum = Integer.parseInt(lastPageNumber);
        ParserValidator.validatePositiveInteger(maxPageNum, this);

        // TODO take logging outside of the parser
        log.info("Found " + "?" + " ads to scrape, a total of " + maxPageNum + " pages.");

        return maxPageNum;
    }

    @Override
    public String parseTitle(Element adInJsoupHtml) throws ParserException {
        setState("parseTitle");

        Element imageDiv = adInJsoupHtml.selectFirst("div[class=image]");
        ParserValidator.validateElement(imageDiv, this, adInJsoupHtml);
        String title = imageDiv.selectFirst("img").attr("alt");
        ParserValidator.validateStringIsNotEmpty(title, this, adInJsoupHtml);

        return title;
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) throws ParserException {
        setState("parseUpc");

        Element hoverElement = adInJsoupHtml.selectFirst("div[class=only-hover]").selectFirst("a");
        ParserValidator.validateElement(hoverElement, this, adInJsoupHtml);
        String upcString = hoverElement.attr("onclick");
        ParserValidator.validateStringIsNotEmpty(upcString, this, adInJsoupHtml);
        String upc = parseIntegerFromString(upcString).toString();
        ParserValidator.validateStringIsNotEmpty(upc, this, adInJsoupHtml);

        return getPREFIX() + upc;
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) throws ParserException {
        setState("parsePrice");

        String priceText = null;
        Element priceElement = adInJsoupHtml.selectFirst("div[class=price]");
        ParserValidator.validateElement(priceElement, this);
        Elements priceElements = priceElement.children();
        if (priceElements.size() == 0) {
            priceText = priceElement.text();
        } else if (priceElements.size() == 2) {
            Element priceEl = priceElement.selectFirst("span[class=price-new]");
            ParserValidator.validateElement(priceEl, this);
            priceText = priceEl.text();
        }

        ParserValidator.validateStringIsNotEmpty(priceText, this, adInJsoupHtml);
        Double price = parseDoubleFromString(priceText);
        ParserValidator.validatePositiveDouble(price, this);
        return price;
    }

    @Override
    public String parseImage(Element adInJsoupHtml) throws ParserException {
        setState("parseImage");

        Element imageDiv = adInJsoupHtml.selectFirst("div[class=image]").selectFirst("img");
        ParserValidator.validateElement(imageDiv, this);
        return imageDiv.attr("src");
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) throws ParserException {
        setState("parseUrl");

        Element imageDiv = adInJsoupHtml.selectFirst("div[class=image]").selectFirst("a");
        ParserValidator.validateElement(imageDiv, this);
        String url = imageDiv.attr("href");
        ParserValidator.validateStringIsNotEmpty(url, this, adInJsoupHtml);
        return url;
    }
}
