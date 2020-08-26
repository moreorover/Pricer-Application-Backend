package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.AbstractParser;
import martin.dev.pricer.scraper.ParserException;
import martin.dev.pricer.scraper.ParserValidator;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class WatchoParser extends AbstractParser {

    public WatchoParser(ParserValidator parserValidator) {
        super(parserValidator, "Watcho", "W_", "https://www.watcho.co.uk", 24, 1);
    }

    @Override
    public String makeNextPageUrl(int pageNum) {
        String[] x = getUrlObject().getUrl().split("\\?page=");
        return x[0] + "?page=" + pageNum;
    }

    @Override
    public void parseListOfAdElements() {
        try {
            Elements parsedElements = getDocument().select("li[class=product]");
            getParserValidator().validate(parsedElements, 1, "parseListOfAdElements", this);
            setElements(parsedElements);
        } catch (ParserException e) {
            setElements(new Elements());
        }
    }

    @Override
    public void parseMaxPageNum() {
        try {
            Elements paginationElements = getDocument().select("li[class^=pagination-item]");
//            getParserValidator().validate(paginationElements, 1, "parseMaxPageNum", this);
            if (paginationElements.size() == 0) {
                log.info("Found " + "?" + " ads to scrape, a total of " + 1 + " pages.");
                setMAX_PAGE_NUMBER(1);
                return;
            }
            Element lastPaginationElement = paginationElements.get(paginationElements.size() - 2);
            getParserValidator().validate(lastPaginationElement, 2, "parseMaxPageNum", this);
            String maxPageNumText = lastPaginationElement.text();
            getParserValidator().validate(maxPageNumText, 3, "parseMaxPageNum", this);
            Integer maxPageNum = parseIntegerFromString(maxPageNumText);
            getParserValidator().validate(maxPageNum, 4, "parseMaxPageNum", this);
            setMAX_PAGE_NUMBER(maxPageNum);
            log.info("Found " + "? " + "ads to scrape, a total of " + maxPageNum + " pages.");
        } catch (ParserException e) {
            setMAX_PAGE_NUMBER(1);
        }
    }

    @Override
    public String parseTitle(Element adInJsoupHtml) {
        try {
            Element titleElement = adInJsoupHtml.selectFirst("h4[class=card-title]");
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
            Element upcElement = adInJsoupHtml.selectFirst("article");
            getParserValidator().validate(upcElement, 1, "parseUpc", this);
            String upc = upcElement.attr("data-entity-id");
            getParserValidator().validate(upc, 2, "parseUpc", this);

            return upc;
        } catch (ParserException e) {
            return "";
        }
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) {
        try {
            Element priceElement = adInJsoupHtml.selectFirst("span[class=price price--withTax]");
            getParserValidator().validate(priceElement, 1, "parsePrice", this);
            String priceString = priceElement.text();
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
            Element imgElement = adInJsoupHtml.selectFirst("img[class^=card-image]");
            getParserValidator().validate(imgElement, 1, "parseImage", this);
            String imgUrl = imgElement.attr("data-src");
            getParserValidator().validate(imgUrl, 2, "parseImage", this);

            return imgUrl;
        } catch (ParserException e) {
            return "";
        }
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) {
        try {
            Element aElement = adInJsoupHtml.selectFirst("a");
            getParserValidator().validate(aElement, 1, "parseUrl", this);
            String url = aElement.attr("href");
            getParserValidator().validate(url, 2, "parseUrl", this);

            return url;
        } catch (ParserException e) {
            return "";
        }
    }
}
