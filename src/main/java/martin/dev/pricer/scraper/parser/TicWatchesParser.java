package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.Parser;
import martin.dev.pricer.scraper.ParserException;
import martin.dev.pricer.scraper.ParserValidator;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class TicWatchesParser extends Parser {

    public TicWatchesParser() {
        super("Tic Watches", "TIC_", "https://www.ticwatches.co.uk/", 24);
    }

    @Override
    public String makeNextPageUrl(String url, int pageNum) {
        setState("makeNextPageUrl");

        if (pageNum == 1) {
            return url;
        }
        String[] x = url.split("\\?page=");
        return x[0] + "?page=" + pageNum;
    }

    @Override
    public Elements parseListOfAdElements(Document pageContentInJsoupHtml) throws ParserException {
        setState("parseListOfAdElements");

        Elements parsedElements = pageContentInJsoupHtml.select("li[class^=col]");
        ParserValidator.validateElements(parsedElements, this);

        return parsedElements;
    }

    @Override
    public int parseMaxPageNum(Document pageContentInJsoupHtml) throws ParserException {
        setState("parseMaxPageNum");

        Element paginationBlockElement = pageContentInJsoupHtml.selectFirst("div[class=product-listings__top__view-all]");
        ParserValidator.validateElement(paginationBlockElement, this);
        String countString = paginationBlockElement.text();
        ParserValidator.validateStringIsNotEmpty(countString, this);
        Integer adsCount = parseIntegerFromString(countString);
        ParserValidator.validatePositiveInteger(adsCount, this);
        Integer maxPageNum = calculateTotalPages(adsCount);
        ParserValidator.validatePositiveInteger(maxPageNum, this);

        // TODO take logging outside of the parser
        log.info("Found " + adsCount + " ads to scrape, a total of " + maxPageNum + " pages.");
        return maxPageNum;
    }

    @Override
    public String parseTitle(Element adInJsoupHtml) throws ParserException {
        setState("parseTitle");

        Element titleElement = adInJsoupHtml.selectFirst("a");
        ParserValidator.validateElement(titleElement, this, adInJsoupHtml);
        String title = titleElement.attr("title");
        ParserValidator.validateStringIsNotEmpty(title, this);

        return title;
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) throws ParserException {
        setState("parseUpc");

        Element upcElement = adInJsoupHtml.selectFirst("div");
        ParserValidator.validateElement(upcElement, this, adInJsoupHtml);
        String upc = upcElement.attr("data-infid");
        ParserValidator.validateStringIsNotEmpty(upc, this);

        return getPREFIX() + upc;
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) throws ParserException {
        setState("parsePrice");

        Element priceElement = adInJsoupHtml.selectFirst("span[class=product-content__price--inc]");
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
        String imgSrc = imgElement.attr("data-src");
        ParserValidator.validateStringIsNotEmpty(imgSrc, this);

        return getBASE_URL() + imgElement.attr("data-src");
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
