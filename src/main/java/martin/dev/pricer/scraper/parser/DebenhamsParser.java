package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.AbstractParser;
import martin.dev.pricer.scraper.ParserException;
import martin.dev.pricer.scraper.ParserValidator;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class DebenhamsParser extends AbstractParser {

    public DebenhamsParser(ParserValidator parserValidator) {
        super(parserValidator, "Debenhams", "DBH_", "https://www.debenhams.com", 60, 1);
    }

    @Override
    public String makeNextPageUrl(int pageNum) {
        String[] x = getUrlObject().getUrl().split("pn=");
        return x[0] + "pn=" + pageNum + "&?shipToCntry=GB";
    }

    @Override
    public void parseListOfAdElements() {
        try {
            Elements parsedElements = getDocument().select("div[class^=c-product-item]");
            getParserValidator().validate(parsedElements, 1, "parseListOfAdElements");
            setElements(parsedElements);
        } catch (ParserException e) {
            setElements(new Elements());
        }
    }

    @Override
    public void parseMaxPageNum() {
        try {
            Element paginationElement = getDocument().selectFirst("div[class*=dbh-count]");
            getParserValidator().validate(paginationElement, 1, "parseMaxPageNum");
            String countString = paginationElement.text();
            getParserValidator().validate(countString, 2, "parseMaxPageNum");
            Integer adsCount = parseIntegerFromString(countString);
            getParserValidator().validate(adsCount, 3, "parseMaxPageNum");
            Integer maxPageNum = calculateTotalPages(adsCount);
            getParserValidator().validate(maxPageNum, 4, "parseMaxPageNum");
            setMAX_PAGE_NUMBER(maxPageNum);
            log.info("Found " + adsCount + " ads to scrape, a total of " + maxPageNum + " pages.");
        } catch (ParserException e) {
            setMAX_PAGE_NUMBER(0);
            e.printStackTrace();
        }
    }

    @Override
    public String parseTitle(Element adInJsoupHtml) {
        try {
            Element titleElement = adInJsoupHtml.selectFirst("h2[class^=c-product-item-title]");
            getParserValidator().validate(titleElement, 1, "parseTitle");
            String title = titleElement.text();
            getParserValidator().validate(title, 2, "parseTitle");
            return title;
        } catch (ParserException e) {
            return "";
        }
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) {
        try {
            Element aElement = adInJsoupHtml.selectFirst("a");
            getParserValidator().validate(aElement, 1, "parseUpc");
            String upcText = aElement.attr("href");
            getParserValidator().validate(upcText, 2, "parseUpc");
            String[] upcTextArray = upcText.split("prod_");
            getParserValidator().validate(upcTextArray, 3, "parseUpc");
            String upc = upcTextArray[1];
            getParserValidator().validate(upc, 4, "parseUpc");

            return getPREFIX() + upc;
        } catch (ParserException e) {
            return "";
        }
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) {
        try {
            Element priceElement = adInJsoupHtml.selectFirst("span[itemprop=price]");
            getParserValidator().validate(priceElement, 1, "parsePrice");
            String priceString = priceElement.text();
            getParserValidator().validate(priceString, 2, "parsePrice");
            Double price = parseDoubleFromString(priceString);
            getParserValidator().validate(price, 3, "parsePrice");

            return price;
        } catch (ParserException e) {
            return 0.0;
        }
    }

    @Override
    public String parseImage(Element adInJsoupHtml) {
        // Not parsing images as these are loaded dynamically with JSS when scrolling.
        return "";
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) {
        try {
            Element aElement = adInJsoupHtml.selectFirst("a");
            getParserValidator().validate(aElement, 1, "parseUrl");
            String url = aElement.attr("href");
            getParserValidator().validate(aElement, 2, "parseUrl");

            return getBASE_URL() + url;
        } catch (ParserException e) {
            return "";
        }
    }
}
