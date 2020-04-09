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
public class GoldSmithsParser extends Parser {

    public GoldSmithsParser() {
        super("Gold Smiths", "GS_", "https://www.goldsmiths.co.uk", 96);
    }

    @Override
    public String makeNextPageUrl(String url, int pageNum) {
        setState("makeNextPageUrl");

        String[] x = url.split("Page_");
        return x[0] + "Page_" + pageNum + "/Psize_96/Show_Page/";
    }

    @Override
    public Elements parseListOfAdElements(Document pageContentInJsoupHtml) throws ParserException {
        setState("parseListOfAdElements");

        Elements parsedElements = pageContentInJsoupHtml.select("div[class=product]");
        ParserValidator.validateElements(parsedElements, this);

        return parsedElements;
    }

    @Override
    public int parseMaxPageNum(Document pageContentInJsoupHtml) throws ParserException {
        setState("parseMaxPageNum");

        Element resultsElement = pageContentInJsoupHtml.selectFirst("p[class^=showAllResults]");
        ParserValidator.validateElement(resultsElement, this);
        resultsElement = resultsElement.selectFirst("span[class=bold]");
        ParserValidator.validateElement(resultsElement, this);
        String countString = resultsElement.text();
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

        Element titleElement = adInJsoupHtml.selectFirst("div[class=product-title]");
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
        String upc = aElement.attr("id");
        ParserValidator.validateStringIsNotEmpty(upc, this);

        return getPREFIX() + upc;
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) throws ParserException {
        setState("parsePrice");

        Element priceElement = adInJsoupHtml.selectFirst("div[class=prodPrice]");
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
        String imgSrc = imgElement.attr("src");
        ParserValidator.validateStringIsNotEmpty(imgSrc, this);
        if (imgSrc.endsWith(".jpg")) {
            return imgSrc;
        }
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
