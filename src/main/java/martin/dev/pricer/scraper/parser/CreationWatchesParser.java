package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.AbstractParser;
import martin.dev.pricer.scraper.ParserException;
import martin.dev.pricer.scraper.ParserValidator;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class CreationWatchesParser extends AbstractParser {

    public CreationWatchesParser(ParserValidator parserValidator) {
        super(parserValidator, "Creation Watches", "CW_", "https://www.creationwatches.com", 60, 1);
    }

    @Override
    public String makeNextPageUrl(int pageNum) {
        String[] x = getUrlObject().getUrl().split("/index-");
        return x[0] + "/index-" + pageNum + "-5d.html?currency=GBP";
    }

    @Override
    public void parseListOfAdElements() {
        try {
            Elements parsedElements = getDocument().select("div[class=product-box]");
            getParserValidator().validate(parsedElements, 1, "parseListOfAdElements");
            setElements(parsedElements);
        } catch (ParserException e) {
            setElements(new Elements());
        }
    }

    @Override
    public void parseMaxPageNum() {
        try {
            Element countBox = getDocument().selectFirst("div[class=display-heading-box]").selectFirst("strong");
            getParserValidator().validate(countBox, 1, "parseMaxPageNum");
            String[] stringArray = countBox.text().split("of");
            getParserValidator().validate(stringArray, 2, "parseMaxPageNum");
            String countString = stringArray[1];
            getParserValidator().validate(countString, 3, "parseMaxPageNum");
            Integer adsCount = parseIntegerFromString(countString);
            getParserValidator().validate(adsCount, 4, "parseMaxPageNum");
            Integer maxPageNum = calculateTotalPages(adsCount);
            getParserValidator().validate(maxPageNum, 5, "parseMaxPageNum");
            setMAX_PAGE_NUMBER(maxPageNum);
        } catch (ParserException e) {
            setMAX_PAGE_NUMBER(0);
            log.warn(e.getMessage());
        }
    }

    @Override
    public String parseTitle(Element adInJsoupHtml) {
        try {
            Element titleElement = adInJsoupHtml.selectFirst("h3[class=product-name]").selectFirst("a");
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
            Element modelElement = adInJsoupHtml.selectFirst("p[class=product-model-no]");
            getParserValidator().validate(modelElement, 1, "parseUpc");
            String upcText = modelElement.text();
            getParserValidator().validate(upcText, 2, "parseUpc");
            String[] stringArray = upcText.split(": ");
            getParserValidator().validate(stringArray, 3, "parseUpc");
            String upc = stringArray[1];
            getParserValidator().validate(upc, 4, "parseUpc");

            return getPREFIX() + upc;
        } catch (ParserException e) {
            return "";
        }
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) {
        try {
            Element titleElement = adInJsoupHtml.selectFirst("p[class=product-price]").selectFirst("span");
            getParserValidator().validate(titleElement, 1, "parsePrice");
            String priceString = titleElement.text();
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
        try {
            Element titleElement = adInJsoupHtml.selectFirst("div[class=product-img-box]").selectFirst("img");
            getParserValidator().validate(titleElement, 1, "parseImage");
            String imgUrl = titleElement.attr("src");
            getParserValidator().validate(imgUrl, 2, "parseImage");

            return imgUrl;
        } catch (ParserException e) {
            return "";
        }
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) {
        try {
            Element titleElement = adInJsoupHtml.selectFirst("h3[class=product-name]").selectFirst("a");
            getParserValidator().validate(titleElement, 1, "parseUrl");
            String url = titleElement.attr("href");
            getParserValidator().validate(url, 2, "parseUrl");

            return url;
        } catch (ParserException e) {
            return "";
        }
    }
}
