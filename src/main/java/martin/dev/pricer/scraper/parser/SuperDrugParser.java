package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.Parser;
import martin.dev.pricer.scraper.ParserException;
import martin.dev.pricer.scraper.ParserI;
import martin.dev.pricer.scraper.ParserValidator;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class SuperDrugParser extends Parser {

    public SuperDrugParser() {
        super("Superdrug", "SD_", "https://www.superdrug.com", 96);
    }

    @Override
    public String makeNextPageUrl(String url, int pageNum) {
        setState("makeNextPageUrl");

        String[] x = url.split("&page=0");
        return x[0] + "&page=0" + pageNum + "&resultsForPage=60&sort=bestBiz";
    }

    @Override
    public Elements parseListOfAdElements(Document pageContentInJsoupHtml) throws ParserException {
        setState("parseListOfAdElements");

        Elements parsedElements = pageContentInJsoupHtml.select("div[class=item__content]");
        ParserValidator.validateElements(parsedElements, this);

        return parsedElements;
    }

    @Override
    public int parseMaxPageNum(Document pageContentInJsoupHtml) throws ParserException {
        setState("parseMaxPageNum");


        Elements paginationElements = pageContentInJsoupHtml.select("ul[class=pagination__list]");
        ParserValidator.validateElements(paginationElements, this);
        paginationElements = paginationElements.select("li");
        ParserValidator.validateElements(paginationElements, this);
        int countOfPaginationElements = paginationElements.size();
        if (countOfPaginationElements == 0) {
            return 0;
        } else {
            String elementText = paginationElements.get(countOfPaginationElements - 2).text();
            ParserValidator.validateStringIsNotEmpty(elementText, this);
            Integer maxPageNum = parseIntegerFromString(elementText);
            ParserValidator.validatePositiveInteger(maxPageNum, this);

            // TODO take logging outside of the parser
            log.info("Found " + "?" + " ads to scrape, a total of " + maxPageNum + " pages.");
            return maxPageNum;
        }
    }

    @Override
    public String parseTitle(Element adInJsoupHtml) throws ParserException {
        setState("parseTitle");

        Element titleElement = adInJsoupHtml.selectFirst("a[class*=item__productName]");
        ParserValidator.validateElement(titleElement, this, adInJsoupHtml);
        String title = titleElement.text();
        ParserValidator.validateStringIsNotEmpty(title, this);

        return title;
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) throws ParserException {
        setState("parseUpc");

        String url = parseUrl(adInJsoupHtml);
        String[] strings = url.split("/p/");
        ParserValidator.validateStringArray(strings, 2, this, adInJsoupHtml);
        String upc = strings[1];
        ParserValidator.validateStringIsNotEmpty(upc, this);

        return getPREFIX() + upc;
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) throws ParserException {
        setState("parsePrice");

        Element priceElement = adInJsoupHtml.selectFirst("span[class*=item__price--now]");
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

        Element imgElement = adInJsoupHtml.selectFirst("img");
        ParserValidator.validateElement(imgElement, this, adInJsoupHtml);

        if (!imgElement.attr("src").equals("")) {
            return getBASE_URL() + "/" + imgElement.attr("src");
        } else {
            return getBASE_URL() + "/" + imgElement.attr("data-src");
        }
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) throws ParserException {
        setState("parseUrl");

        Element aElement = adInJsoupHtml.selectFirst("a[class*=item__productName]");
        ParserValidator.validateElement(aElement, this, adInJsoupHtml);
        String url = aElement.attr("href");
        ParserValidator.validateStringIsNotEmpty(url, this);

        return getBASE_URL() + "/" + url;
    }
}
