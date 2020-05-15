package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.AbstractParser;
import martin.dev.pricer.scraper.ParserException;
import martin.dev.pricer.scraper.ParserValidator;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class AMJWatchesParser extends AbstractParser {

    public AMJWatchesParser(ParserValidator parserValidator) {
        super(parserValidator, "AMJ Watches", "AMJW_", "https://amjwatches.co.uk", 40, 1);
    }

    @Override
    public String makeNextPageUrl(int pageNum) {
        String[] x = getUrlObject().getUrl().split("page=");
        return x[0] + "page=" + pageNum;
    }

    @Override
    public void parseListOfAdElements() {
        try {
            Elements parsedElements = getDocument().select("div[class=watch-sec]");
            getParserValidator().validate(parsedElements, 1, "parseListOfAdElements", this);
            setElements(parsedElements);
        } catch (ParserException e) {
            setElements(new Elements());
        }
    }

    @Override
    public void parseMaxPageNum() {
        try {
            Element productsCountElement = getDocument().selectFirst("div[class=items-on-page]");
            getParserValidator().validate(productsCountElement, 1, "maxPageNum", this);
            String countText = productsCountElement.children().last().text();
            getParserValidator().validate(countText, 2, "maxPageNum", this);
            Integer adsCount = parseIntegerFromString(countText);
            getParserValidator().validate(adsCount, 3, "maxPageNum", this);
            Integer maxPage = calculateTotalPages(adsCount);
            getParserValidator().validate(maxPage, 4, "maxPageNum", this);
            // TODO take logging outside of the parser
            log.info("Found " + adsCount + " ads to scrape, a total of " + maxPage + " pages.");
        } catch (ParserException e) {
            setMAX_PAGE_NUMBER(0);
        }
    }

    @Override
    public String parseTitle(Element adInJsoupHtml) {
        try {
            Element imgElement = adInJsoupHtml.selectFirst("div[class=watch-image]").selectFirst("a");
            getParserValidator().validate(imgElement, 1, "parseTitle", this);
            String title = imgElement.attr("title");
            getParserValidator().validate(imgElement, 2, "parseTitle", this);

            return title;
        } catch (
                ParserException e) {
            return "";
        }

    }

    @Override
    public String parseUpc(Element adInJsoupHtml) {
        try {
            Element imgElement = adInJsoupHtml.selectFirst("div[class=watch-image]").selectFirst("a");
            getParserValidator().validate(imgElement, 1, "parseUpc", this);
            String url = imgElement.attr("href");
            getParserValidator().validate(imgElement, 2, "parseUpc", this);
            String[] urlArray = url.split(".uk/");
            getParserValidator().validate(urlArray, 3, "parseUpc", this);

            return getPREFIX() + urlArray[1];
        } catch (ParserException e) {
            return "";
        }
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) {
        try {
            Element detailsElement = adInJsoupHtml.selectFirst("div[class=watch-details]").children().last();
            getParserValidator().validate(detailsElement, 1, "parsePrice", this);
            String priceString = detailsElement.text();
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
            Element imgElement = adInJsoupHtml.selectFirst("div[class=watch-image]").selectFirst("a").selectFirst("img");
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
            Element imgElement = adInJsoupHtml.selectFirst("div[class=watch-image]").selectFirst("a");
            getParserValidator().validate(imgElement, 1, "parseUrl", this);
            String url = imgElement.attr("href");
            getParserValidator().validate(imgElement, 2, "parseUrl", this);

            return url;
        } catch (ParserException e) {
            return "";
        }
    }
}
