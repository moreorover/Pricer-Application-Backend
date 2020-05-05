package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.AbstractParser;
import martin.dev.pricer.scraper.ParserException;
import martin.dev.pricer.scraper.ParserValidator;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class ArgosParser extends AbstractParser {

    public ArgosParser(ParserValidator parserValidator) {
        super(parserValidator, "Argos", "A_", "https://www.argos.co.uk", 30, 1);
    }

    @Override
    public String makeNextPageUrl(int pageNum) {
        if (pageNum == 1) {
            return getUrlObject().getUrl();
        }
        String[] x = getUrlObject().getUrl().split("page:");
        return x[0] + "page:" + pageNum;
    }

    @Override
    public void parseListOfAdElements() {
        try {
            Elements parsedElements = getDocument().select("div[class^=ProductCardstyles__Wrapper-]");
            getParserValidator().validate(parsedElements, 1, "parseListOfAdElements", this);
            setElements(parsedElements);
        } catch (ParserException e) {
            setElements(new Elements());
        }
    }

    @Override
    public void parseMaxPageNum() {
        try {
            Element searchResultsCount = getDocument().selectFirst("div[class*=search-results-count]");
            getParserValidator().validate(searchResultsCount, 1, "maxPageNum", this);
            String countString = searchResultsCount.attr("data-search-results");
            getParserValidator().validate(countString, 2, "maxPageNum", this);
            Integer adsCount = parseIntegerFromString(countString);
            getParserValidator().validate(adsCount, 3, "maxPageNum", this);
            Integer maxPageNum = calculateTotalPages(adsCount);
            getParserValidator().validate(maxPageNum, 4, "maxPageNum", this);
            setMAX_PAGE_NUMBER(maxPageNum);
            log.info("Found " + adsCount + "ads to scrape, a total of " + maxPageNum + " pages.");
        } catch (ParserException e) {
            setMAX_PAGE_NUMBER(0);
            e.printStackTrace();
        }
    }

    @Override
    public String parseTitle(Element adInJsoupHtml) {
        try {
            Element titleElement = adInJsoupHtml.selectFirst("a[class*=Title]");
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
            String upc = adInJsoupHtml.attr("data-product-id");
            getParserValidator().validate(upc, 1, "parseUpc", this);

            return getPREFIX() + upc;
        } catch (ParserException e) {
            return "";
        }
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) {
        try {
            Element priceElement = adInJsoupHtml.selectFirst("div[class*=PriceText]");
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
//        try {
//            Element imgElement = adInJsoupHtml.selectFirst("div[class*=ImageWrapper]");
//            getParserValidator().validate(imgElement, 1, "parseImage");
//            imgElement = imgElement.selectFirst("picture");
//            getParserValidator().validate(imgElement, 2, "parseImage");
//            imgElement = imgElement.selectFirst("img");
//            getParserValidator().validate(imgElement, 3, "parseImage");
//            String imgUrl = imgElement.attr("src");
//            getParserValidator().validate(imgElement, 4, "parseImage");
//            return imgUrl;
//        } catch (ParserException e) {
//            return "";
//        }
        return "";
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) {
        try {
            Element urlElement = adInJsoupHtml.selectFirst("a");
            getParserValidator().validate(urlElement, 1, "parseUrl", this);
            String url = urlElement.attr("href");
            getParserValidator().validate(url, 2, "parseUrl", this);
            return getBASE_URL() + url;
        } catch (ParserException e) {
            return "";
        }
    }
}
