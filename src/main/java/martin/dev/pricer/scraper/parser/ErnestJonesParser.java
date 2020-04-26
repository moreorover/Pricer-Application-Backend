package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.Parser;
import martin.dev.pricer.scraper.ParserException;
import martin.dev.pricer.scraper.ParserValidator;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class ErnestJonesParser extends Parser {

    public ErnestJonesParser() {
        super("Ernest Jones", "EJ_", "https://www.ernestjones.co.uk", 24);
    }

    @Override
    public String makeNextPageUrl(String url, int pageNum) {
        setState("makeNextPageUrl");

        String[] x = url.split("Pg=");
        return x[0] + "Pg=" + pageNum;
    }

    @Override
    public Elements parseListOfAdElements(Document pageContentInJsoupHtml) throws ParserException {
        setState("parseListOfAdElements");

        Elements parsedElements = pageContentInJsoupHtml.select("li[class^=product-tile-list__item]");
        ParserValidator.validateElements(parsedElements, this);

        return parsedElements;
    }

    @Override
    public int parseMaxPageNum(Document pageContentInJsoupHtml) throws ParserException {
        setState("parseMaxPageNum");

        Element paginationBlockElement = pageContentInJsoupHtml.selectFirst("ol[class*=pageNumbers]");
        ParserValidator.validateElement(paginationBlockElement, this);
        Elements paginationButtons = paginationBlockElement.select("li");
        ParserValidator.validateElements(paginationButtons, this);
        Element lastPageElement = paginationButtons.get(7);
        ParserValidator.validateElement(lastPageElement, this);
        String lastPageText = lastPageElement.text();
        ParserValidator.validateStringIsNotEmpty(lastPageText, this);
        int maxPageNum = parseIntegerFromString(lastPageText);
        ParserValidator.validatePositiveInteger(maxPageNum, this);

        // TODO take logging outside of the parser
        log.info("Found " + "?" + " ads to scrape, a total of " + maxPageNum + " pages.");
        return maxPageNum;
    }

    @Override
    public String parseTitle(Element adInJsoupHtml) throws ParserException {
        setState("parseTitle");

        Element titleElement = adInJsoupHtml.selectFirst("p[class=product-tile__description]");
        ParserValidator.validateElement(titleElement, this, adInJsoupHtml);
        String title = titleElement.text();
        ParserValidator.validateStringIsNotEmpty(title, this, adInJsoupHtml);

        return title;
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) throws ParserException {
        setState("parseUpc");

        String url = parseUrl(adInJsoupHtml);
        String[] strings = url.split("/d/");
        ParserValidator.validateStringArray(strings, 2, this, adInJsoupHtml);
        strings = strings[1].split("/");
        ParserValidator.validateStringArray(strings, 2, this, adInJsoupHtml);
        String upc = strings[0];
        ParserValidator.validateStringIsNotEmpty(upc, this, adInJsoupHtml);

        return getPREFIX() + upc;
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) throws ParserException {
        setState("parsePrice");

        Element priceElement = adInJsoupHtml.selectFirst("p[class*=current-price]");
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

        Element imgElement = adInJsoupHtml.selectFirst("img[class^=product-tile__image]");
        ParserValidator.validateElement(imgElement, this, adInJsoupHtml);
        String imgUrl = imgElement.attr("data-src");
        ParserValidator.validateStringIsNotEmpty(imgUrl, this, adInJsoupHtml);

        return imgUrl;
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) throws ParserException {
        setState("parseUrl");

        Element aElement = adInJsoupHtml.select("a").first();
        ParserValidator.validateElement(aElement, this, adInJsoupHtml);
        String url = aElement.attr("href");
        ParserValidator.validateStringIsNotEmpty(url, this, adInJsoupHtml);

        return getBASE_URL() + url;
    }
}
