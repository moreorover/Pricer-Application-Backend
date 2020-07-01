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
            getParserValidator().validate(parsedElements, 1, "parseListOfAdElements", this);
            setElements(parsedElements);
        } catch (ParserException e) {
            setElements(new Elements());
        }
    }

    @Override
    public void parseMaxPageNum() {
        try {
            Element countBox = getDocument().selectFirst("div[class=display-heading-box]");
            getParserValidator().validate(countBox, 1, "parseMaxPageNum", this);
            countBox = countBox.selectFirst("strong");
            getParserValidator().validate(countBox, 2, "parseMaxPageNum", this);
            String[] stringArray = countBox.text().split("of");
            getParserValidator().validate(stringArray, 3, "parseMaxPageNum", this);
            String countString = stringArray[1];
            getParserValidator().validate(countString, 4, "parseMaxPageNum", this);
            Integer adsCount = parseIntegerFromString(countString);
            getParserValidator().validate(adsCount, 5, "parseMaxPageNum", this);
            Integer maxPageNum = calculateTotalPages(adsCount);
            getParserValidator().validate(maxPageNum, 6, "parseMaxPageNum", this);
            setMAX_PAGE_NUMBER(maxPageNum);
        } catch (ParserException e) {
            setMAX_PAGE_NUMBER(0);
        }
    }

    @Override
    public String parseTitle(Element adInJsoupHtml) {
        try {
            Element titleElement = adInJsoupHtml.selectFirst("h3[class=product-name]").selectFirst("a");
            getParserValidator().validate(titleElement, 1, "parseTitle", this);
            String title = titleElement.text();
            getParserValidator().validate(title, 2, "parseTitle", this);

            return title;
        } catch (ParserException e) {
            return "";
        }
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) {
        try {
            Element modelElement = adInJsoupHtml.selectFirst("p[class=product-model-no]");
            getParserValidator().validate(modelElement, 1, "parseUpc", this);
            String upcText = modelElement.text();
            getParserValidator().validate(upcText, 2, "parseUpc", this);
            String[] stringArray = upcText.split(": ");
            getParserValidator().validate(stringArray, 3, "parseUpc", this);
            String upc = stringArray[1];
            getParserValidator().validate(upc, 4, "parseUpc", this);

            return getPREFIX() + upc;
        } catch (ParserException e) {
            return "";
        }
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) {
        try {
            Element titleElement = adInJsoupHtml.selectFirst("p[class=product-price]").selectFirst("span");
            getParserValidator().validate(titleElement, 1, "parsePrice", this);
            String priceString = titleElement.text();
            getParserValidator().validate(priceString, 2, "parsePrice", this);
            Double price = parseDoubleFromString(priceString);
            getParserValidator().validate(price, 3, "parsePrice", this);

            return price;
        } catch (ParserException e) {
            return 0.0;
        }
    }

    @Override
    public String parseImage(Element adInJsoupHtml) {
        try {
            Element titleElement = adInJsoupHtml.selectFirst("div[class=product-img-box]").selectFirst("img");
            getParserValidator().validate(titleElement, 1, "parseImage", this);
            String imgUrl = titleElement.attr("src");
            getParserValidator().validate(imgUrl, 2, "parseImage", this);

            return imgUrl;
        } catch (ParserException e) {
            return "";
        }
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) {
        try {
            Element titleElement = adInJsoupHtml.selectFirst("h3[class=product-name]").selectFirst("a");
            getParserValidator().validate(titleElement, 1, "parseUrl", this);
            String url = titleElement.attr("href");
            getParserValidator().validate(url, 2, "parseUrl", this);

            return url;
        } catch (ParserException e) {
            return "";
        }
    }
}
