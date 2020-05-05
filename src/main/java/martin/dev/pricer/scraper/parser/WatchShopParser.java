package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.AbstractParser;
import martin.dev.pricer.scraper.ParserException;
import martin.dev.pricer.scraper.ParserValidator;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class WatchShopParser extends AbstractParser {

    public WatchShopParser(ParserValidator parserValidator) {
        super(parserValidator, "Watch Shop", "WS_", "https://www.watchshop.com", 192, 1);
    }

    @Override
    public String makeNextPageUrl(int pageNum) {
        String[] x = getUrlObject().getUrl().split("&page=");
        return x[0] + "&page=" + pageNum;
    }

    @Override
    public void parseListOfAdElements() {
        try {
            Elements parsedElements = getDocument().select("div[class*=product-container]");
            getParserValidator().validate(parsedElements, 1, "parseListOfAdElements");
            setElements(parsedElements);
        } catch (ParserException e) {
            setElements(new Elements());
        }
    }

    @Override
    public void parseMaxPageNum() {
        try {
            Elements showResults = getDocument().select("div[class=show-results]");
            getParserValidator().validate(showResults, 1, "parseMaxPageNum");
            String results = showResults.text();
            getParserValidator().validate(results, 2, "parseMaxPageNum");
            String[] resultsArray = results.split(" of ");
            getParserValidator().validate(resultsArray, 3, "parseMaxPageNum");
            String result = resultsArray[1];
            getParserValidator().validate(result, 4, "parseMaxPageNum");
            Integer adsCount = parseIntegerFromString(result);
            getParserValidator().validate(adsCount, 5, "parseMaxPageNum");
            Integer maxPageNum = calculateTotalPages(adsCount);
            getParserValidator().validate(maxPageNum, 6, "parseMaxPageNum");
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
            Element titleElement = adInJsoupHtml.selectFirst("meta[itemprop=name]");
            getParserValidator().validate(titleElement, 1, "parseTitle");
            String title = titleElement.attr("content").trim();
            getParserValidator().validate(title, 2, "parseTitle");

            return title;
        } catch (ParserException e) {
            return "";
        }
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) {
        try {
            Element upcElement = adInJsoupHtml.selectFirst("meta[itemprop=sku]");
            getParserValidator().validate(upcElement, 1, "parseUpc");
            String upc = upcElement.attr("content");
            getParserValidator().validate(upc, 2, "parseUpc");

            return getPREFIX() + upc;
        } catch (ParserException e) {
            return "";
        }
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) {
        try {
            Element priceElement = adInJsoupHtml.selectFirst("div[class=product-price]");
            getParserValidator().validate(priceElement, 1, "parsePrice");
            priceElement = priceElement.selectFirst("strong");
            getParserValidator().validate(priceElement, 2, "parsePrice");
            String priceString = priceElement.text();
            getParserValidator().validate(priceString, 3, "parsePrice");
            Double price = parseDoubleFromString(priceString);
            getParserValidator().validate(price, 4, "parsePrice");

            return price;
        } catch (ParserException e) {
            return 0.0;
        }
    }

    @Override
    public String parseImage(Element adInJsoupHtml) {
        try {
            Element imgElement = adInJsoupHtml.selectFirst("div[class=product-img]");
            getParserValidator().validate(imgElement, 1, "parseImage");
            imgElement = imgElement.selectFirst("img");
            getParserValidator().validate(imgElement, 2, "parseImage");
            String imgSrc = imgElement.attr("src");
            getParserValidator().validate(imgSrc, 3, "parseImage");
            if (imgSrc.endsWith("loader_border.gif")) {
                return "";
            }
            return imgSrc;
        } catch (ParserException e) {
            return "";
        }
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) {
        try {
            Element imgElement = adInJsoupHtml.selectFirst("div[class=product-img]");
            getParserValidator().validate(imgElement, 1, "parseUrl");
            imgElement = imgElement.selectFirst("a");
            getParserValidator().validate(imgElement, 2, "parseUrl");
            String url = imgElement.attr("href");
            getParserValidator().validate(url, 3, "parseUrl");

            return getBASE_URL() + url;
        } catch (ParserException e) {
            return "";
        }
    }
}
