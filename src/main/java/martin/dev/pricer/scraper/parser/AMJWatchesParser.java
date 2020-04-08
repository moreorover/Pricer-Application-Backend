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
    public String parseTitle(Element adInJsoupHtml) {
        Element imgElement = adInJsoupHtml.selectFirst("div[class=watch-image]");
        imgElement = imgElement.selectFirst("a");
        return imgElement.attr("title");
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) {
        try {
            Element imgElement = adInJsoupHtml.selectFirst("div[class=watch-image]");
            imgElement = imgElement.selectFirst("a");
            String url = imgElement.attr("href");
            url = url.split(".uk/")[1];
            return PREFIX + url;
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) {
        Element detailsElement = adInJsoupHtml.selectFirst("div[class=watch-details]");

        String priceString = detailsElement.children().last().text().replaceAll("[^\\d.]", "");
        return Double.parseDouble(priceString);
    }

    @Override
    public String parseImage(Element adInJsoupHtml) {
        Element imgElement = adInJsoupHtml.selectFirst("div[class=watch-image]");
        imgElement = imgElement.selectFirst("a");
        imgElement = imgElement.selectFirst("img");
        return imgElement.attr("data-src");
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) {
        Element imgElement = adInJsoupHtml.selectFirst("div[class=watch-image]");
        imgElement = imgElement.selectFirst("a");
        return imgElement.attr("href");
    }
}
