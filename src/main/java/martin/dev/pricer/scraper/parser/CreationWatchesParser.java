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
public class CreationWatchesParser extends Parser {

    public CreationWatchesParser() {
        super("Creation Watches", "CW_", "https://www.creationwatches.com", 60);
    }

    @Override
    public String makeNextPageUrl(String url, int pageNum) {
        setState("makeNextPageUrl");

        String[] x = url.split("/index-");
        return x[0] + "/index-" + pageNum + "-5d.html?currency=GBP";
    }

    @Override
    public Elements parseListOfAdElements(Document pageContentInJsoupHtml) throws ParserException {
        setState("parseListOfAdElements");

        Elements parsedElements = pageContentInJsoupHtml.select("div[class=product-box]");
        ParserValidator.validateElements(parsedElements, this);

        return parsedElements;
    }

    @Override
    public int parseMaxPageNum(Document pageContentInJsoupHtml) throws ParserException {
        setState("parseMaxPageNum");

        Element countBox = pageContentInJsoupHtml.selectFirst("div[class=display-heading-box]").selectFirst("strong");
        ParserValidator.validateElement(countBox, this);
        String[] stringArray = countBox.text().split("of");
        ParserValidator.validateStringArray(stringArray, 2, this);
        String countString = stringArray[1];
        ParserValidator.validateStringIsNotEmpty(countString, this);
        Integer adsCount = parseIntegerFromString(countString);
        ParserValidator.validatePositiveInteger(adsCount, this);
        Integer maxPageNum = calculateTotalPages(adsCount);

        // TODO take logging outside of the parser
        log.info("Found " + adsCount + "ads to scrape, a total of " + maxPageNum + " pages.");
        return maxPageNum;
    }

    @Override
    public String parseTitle(Element adInJsoupHtml) throws ParserException {
        setState("parseTitle");

        Element titleElement = adInJsoupHtml.selectFirst("h3[class=product-name]").selectFirst("a");
        ParserValidator.validateElement(titleElement, this, adInJsoupHtml);
        String title = titleElement.text();
        ParserValidator.validateStringIsNotEmpty(title, this);

        return title;
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) throws ParserException {
        setState("parseUpc");

        Element modelElement = adInJsoupHtml.selectFirst("p[class=product-model-no]");
        ParserValidator.validateElement(modelElement, this, adInJsoupHtml);
        String upcText = modelElement.text();
        ParserValidator.validateStringIsNotEmpty(upcText, this);
        String[] stringArray = upcText.split(": ");
        ParserValidator.validateStringArray(stringArray, 2, this, adInJsoupHtml);
        String upc = stringArray[1];
        ParserValidator.validateStringIsNotEmpty(upc, this);

        return getPREFIX() + upc;
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) throws ParserException {
        setState("parsePrice");

        Element titleElement = adInJsoupHtml.selectFirst("p[class=product-price]").selectFirst("span");
        ParserValidator.validateElement(titleElement, this, adInJsoupHtml);
        String priceString = titleElement.text();
        ParserValidator.validateStringIsNotEmpty(priceString, this);
        Double price = parseDoubleFromString(priceString);
        ParserValidator.validatePositiveDouble(price, this);

        return price;
    }

    @Override
    public String parseImage(Element adInJsoupHtml) throws ParserException {
        setState("parseImage");

        Element titleElement = adInJsoupHtml.selectFirst("div[class=product-img-box]").selectFirst("img");
        ParserValidator.validateElement(titleElement, this, adInJsoupHtml);
        String imgUrl = titleElement.attr("src");
        ParserValidator.validateStringIsNotEmpty(imgUrl, this);

        return imgUrl;
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) throws ParserException {
        setState("parseUrl");

        Element titleElement = adInJsoupHtml.selectFirst("h3[class=product-name]").selectFirst("a");
        ParserValidator.validateElement(titleElement, this, adInJsoupHtml);
        String url = titleElement.attr("href");
        ParserValidator.validateStringIsNotEmpty(url, this);

        return url;
    }
}
