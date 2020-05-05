package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.AbstractParser;
import martin.dev.pricer.scraper.ParserException;
import martin.dev.pricer.scraper.ParserValidator;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class FirstClassWatchesParser extends AbstractParser {

    public FirstClassWatchesParser(ParserValidator parserValidator) {
        super(parserValidator, "First Class Watches", "FCW_", "https://www.firstclasswatches.co.uk", 96, 1);
    }

    @Override
    public String makeNextPageUrl(int pageNum) {
        String[] x = getUrlObject().getUrl().split("&page=");
        return x[0] + "&page=" + pageNum;
    }

    @Override
    public void parseListOfAdElements() {
        try {
            Elements parsedElements = getDocument().select("a[class=listingproduct]");
            getParserValidator().validate(parsedElements, 1, "parseListOfAdElements");
            setElements(parsedElements);
        } catch (ParserException e) {
            setElements(new Elements());
        }
    }

    @Override
    public void parseMaxPageNum() {
        try {
            Elements showResults = getDocument().select("span[class=tablet-inline]");
            getParserValidator().validate(showResults, 1, "parseMaxPageNum");
            String maxPageNumText = showResults.text();
            getParserValidator().validate(maxPageNumText, 2, "parseMaxPageNum");
            Integer maxPageNum = parseIntegerFromString(maxPageNumText);
            getParserValidator().validate(maxPageNum, 3, "parseMaxPageNum");
            setMAX_PAGE_NUMBER(maxPageNum);
            log.info("Found " + "? " + "ads to scrape, a total of " + maxPageNum + " pages.");
        } catch (ParserException e) {
            setMAX_PAGE_NUMBER(0);
            e.printStackTrace();
        }
    }

    @Override
    public String parseTitle(Element adInJsoupHtml) {
        try {
            String title = adInJsoupHtml.attr("title").trim();
            getParserValidator().validate(title, 1, "parseTitle");

            return title;
        } catch (ParserException e) {
            return "";
        }
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) {
        try {
            String upc = adInJsoupHtml.attr("data-id");
            getParserValidator().validate(upc, 1, "parseUpc");

            return getPREFIX() + upc;
        } catch (ParserException e) {
            return "";
        }
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) {
        try {
            String priceString = adInJsoupHtml.attr("data-price");
            getParserValidator().validate(priceString, 1, "parsePrice");
            Double price = parseDoubleFromString(priceString);
            getParserValidator().validate(price, 2, "parsePrice");

            return price;
        } catch (ParserException e) {
            return 0.0;
        }
    }

    @Override
    public String parseImage(Element adInJsoupHtml) {
        try {
            Element imgElement = adInJsoupHtml.selectFirst("div[class=image]").selectFirst("img");
            getParserValidator().validate(imgElement, 1, "parseImage");
            String imgUrl = imgElement.attr("src");
            getParserValidator().validate(imgUrl, 2, "parseImage");
            if (imgUrl.endsWith("loader_border.gif")) {
                return "";
            }
            return imgUrl;
        } catch (ParserException e) {
            return "";
        }
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) {
        try {
            String url = adInJsoupHtml.attr("href");
            getParserValidator().validate(url, 1, "parseUrl");

            return url;
        } catch (ParserException e) {
            return "";
        }
    }
}
