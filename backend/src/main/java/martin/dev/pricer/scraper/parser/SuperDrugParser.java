package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.AbstractParser;
import martin.dev.pricer.scraper.ParserException;
import martin.dev.pricer.scraper.ParserValidator;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class SuperDrugParser extends AbstractParser {

    public SuperDrugParser(ParserValidator parserValidator) {
        super(parserValidator, "Superdrug", "SD_", "https://www.superdrug.com", 96, 0);
    }

    @Override
    public String makeNextPageUrl(int pageNum) {
        String[] x = getUrlObject().getUrl().split("&page=0");
        return x[0] + "&page=0" + pageNum + "&resultsForPage=60&sort=bestBiz";
    }

    @Override
    public void parseListOfAdElements() {
        try {
            Elements parsedElements = getDocument().select("div[class=item__content]");
            getParserValidator().validate(parsedElements, 1, "parseListOfAdElements", this);
            setElements(parsedElements);
        } catch (ParserException e) {
            setElements(new Elements());
        }
    }

    @Override
    public void parseMaxPageNum() {
        try {
            Elements paginationElements = getDocument().select("ul[class=pagination__list]");
            getParserValidator().validate(paginationElements, 1, "parseMaxPageNum", this);
            paginationElements = paginationElements.select("li");
            getParserValidator().validate(paginationElements, 2, "parseMaxPageNum", this);
            int countOfPaginationElements = paginationElements.size();
            if (countOfPaginationElements == 0) {
                setMAX_PAGE_NUMBER(0);
            } else {
                Element element = paginationElements.get(countOfPaginationElements - 2);
                getParserValidator().validate(element, 3, "parseMaxPageNum", this);
                String elementText = element.text();
                getParserValidator().validate(elementText, 4, "parseMaxPageNum", this);
                Integer maxPageNum = parseIntegerFromString(elementText);
                getParserValidator().validate(maxPageNum, 5, "parseMaxPageNum", this);
                setMAX_PAGE_NUMBER(maxPageNum);
                log.info("Found " + "? " + "ads to scrape, a total of " + maxPageNum + " pages.");
            }
        } catch (ParserException e) {
            setMAX_PAGE_NUMBER(0);
        }

    }

    @Override
    public String parseTitle(Element adInJsoupHtml) {
        try {
            Element titleElement = adInJsoupHtml.selectFirst("a[class*=item__productName]");
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
            String url = parseUrl(adInJsoupHtml);
            getParserValidator().validate(url, 1, "parseUpc", this);
            String[] strings = url.split("/p/");
            getParserValidator().validate(strings, 2, "parseUpc", this);
            String upc = strings[1];
            getParserValidator().validate(upc, 3, "parseUpc", this);

            return getPREFIX() + upc;
        } catch (ParserException e) {
            return "";
        }
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) {
        try {
            Element priceElement = adInJsoupHtml.selectFirst("span[class*=item__price--now]");
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
            Element imgElement = adInJsoupHtml.selectFirst("img");
            getParserValidator().validate(imgElement, 1, "parseImage", this);

            if (!imgElement.attr("src").equals("")) {
                String imgElementText = imgElement.attr("src");
                getParserValidator().validate(imgElementText, 2, "parseImage", this);
                return getBASE_URL() + "/" + imgElementText;
            } else {
                String imgElementText = imgElement.attr("data-src");
                getParserValidator().validate(imgElementText, 3, "parseImage", this);
                return getBASE_URL() + "/" + imgElementText;
            }
        } catch (ParserException e) {
            return "";
        }
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) {
        try {
            Element aElement = adInJsoupHtml.selectFirst("a[class*=item__productName]");
            getParserValidator().validate(aElement, 1, "parseUrl", this);
            String url = aElement.attr("href");
            getParserValidator().validate(url, 2, "parseUrl", this);

            return getBASE_URL() + "/" + url;
        } catch (ParserException e) {
            return "";
        }
    }
}
