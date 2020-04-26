package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.Parser;
import martin.dev.pricer.scraper.ParserException;
import martin.dev.pricer.scraper.ParserValidator;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class AMJWatchesParser extends Parser {

    public AMJWatchesParser() {
        super("AMJ Watches", "AMJW_", "https://amjwatches.co.uk", 40);
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

        Elements parsedElements = pageContentInJsoupHtml.select("div[class=watch-sec]");
        ParserValidator.validateElements(parsedElements, this);

        return parsedElements;
    }

    @Override
    public int parseMaxPageNum(Document pageContentInJsoupHtml) throws ParserException {
        setState("parseMaxPageNum");

        Element productsCountElement = pageContentInJsoupHtml.selectFirst("div[class=items-on-page]");
        ParserValidator.validateElement(productsCountElement, this);
        String countText = productsCountElement.children().last().text();
        ParserValidator.validateStringIsNotEmpty(countText, this);
        Integer adsCount = parseIntegerFromString(countText);
        ParserValidator.validatePositiveInteger(adsCount, this);
        Integer maxPage = calculateTotalPages(adsCount);

        // TODO take logging outside of the parser
        log.info("Found " + adsCount + " ads to scrape, a total of " + maxPage + " pages.");
        return maxPage;
    }

    @Override
    public String parseTitle(Element adInJsoupHtml) throws ParserException {
        setState("parseTitle");

        Element imgElement = adInJsoupHtml.selectFirst("div[class=watch-image]").selectFirst("a");
        ParserValidator.validateElement(imgElement, this, adInJsoupHtml);
        String title = imgElement.attr("title");
        ParserValidator.validateStringIsNotEmpty(title, this, adInJsoupHtml);

        return title;
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) throws ParserException {
        setState("parseUpc");

        Element imgElement = adInJsoupHtml.selectFirst("div[class=watch-image]").selectFirst("a");
        ParserValidator.validateElement(imgElement, this, adInJsoupHtml);
        String url = imgElement.attr("href");
        ParserValidator.validateStringIsNotEmpty(url, this, adInJsoupHtml);
        String[] urlArray = url.split(".uk/");
        ParserValidator.validateStringArray(urlArray, 2, this, adInJsoupHtml);

        return getPREFIX() + urlArray[1];
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) throws ParserException {
        setState("parsePrice");

        Element detailsElement = adInJsoupHtml.selectFirst("div[class=watch-details]").children().last();
        ParserValidator.validateElement(detailsElement, this, adInJsoupHtml);
        String priceString = detailsElement.text();
        ParserValidator.validateStringIsNotEmpty(priceString, this, adInJsoupHtml);
        Double price = parseDoubleFromString(priceString);
        ParserValidator.validatePositiveDouble(price, this);

        return price;
    }

    @Override
    public String parseImage(Element adInJsoupHtml) throws ParserException {
        setState("parseImage");

        Element imgElement = adInJsoupHtml.selectFirst("div[class=watch-image]").selectFirst("a").selectFirst("img");
        ParserValidator.validateElement(imgElement, this, adInJsoupHtml);
        String imgUrl = imgElement.attr("data-src");
        ParserValidator.validateStringIsNotEmpty(imgUrl, this, adInJsoupHtml);

        return imgUrl;
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) throws ParserException {
        setState("parseUrl");

        Element imgElement = adInJsoupHtml.selectFirst("div[class=watch-image]").selectFirst("a");
        ParserValidator.validateElement(imgElement, this, adInJsoupHtml);
        String url = imgElement.attr("href");
        ParserValidator.validateStringIsNotEmpty(url, this, adInJsoupHtml);

        return url;
    }
}
