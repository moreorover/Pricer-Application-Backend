package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.AbstractParser;
import martin.dev.pricer.scraper.ParserException;
import martin.dev.pricer.scraper.ParserValidator;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class GoldSmithsParser extends AbstractParser {

    public GoldSmithsParser(ParserValidator parserValidator) {
        super(parserValidator, "Gold Smiths", "GS_", "https://www.goldsmiths.co.uk", 96, 1);
    }

    @Override
    public String makeNextPageUrl(int pageNum) {
        String[] x = getUrlObject().getUrl().split("Page_");
        return x[0] + "Page_" + pageNum + "/Psize_96/Show_Page/";
    }

    @Override
    public void parseListOfAdElements() {
        try {
            Elements parsedElements = getDocument().select("div[class=product]");
            getParserValidator().validate(parsedElements, 1, "parseListOfAdElements", this);
            setElements(parsedElements);
        } catch (ParserException e) {
            setElements(new Elements());
        }
    }

    @Override
    public void parseMaxPageNum() {
        try {
            Element resultsElement = getDocument().selectFirst("p[class^=showAllResults]");
            getParserValidator().validate(resultsElement, 1, "parseMaxPageNum", this);
            resultsElement = resultsElement.selectFirst("span[class=bold]");
            getParserValidator().validate(resultsElement, 2, "parseMaxPageNum", this);
            String countString = resultsElement.text();
            getParserValidator().validate(countString, 3, "parseMaxPageNum", this);
            Integer adsCount = parseIntegerFromString(countString);
            getParserValidator().validate(adsCount, 4, "parseMaxPageNum", this);
            Integer maxPageNum = calculateTotalPages(adsCount);
            getParserValidator().validate(maxPageNum, 5, "parseMaxPageNum", this);
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
            Element titleElement = adInJsoupHtml.selectFirst("div[class=product-title]");
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
            Element aElement = adInJsoupHtml.selectFirst("a");
            getParserValidator().validate(aElement, 1, "parseUpc", this);
            String upc = aElement.attr("id");
            getParserValidator().validate(upc, 2, "parseUpc", this);

            return getPREFIX() + upc;
        } catch (ParserException e) {
            return "";
        }
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) {
        try {
            Element priceElement = adInJsoupHtml.selectFirst("div[class=prodPrice]");
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
            String imgSrc = imgElement.attr("src");
            getParserValidator().validate(imgSrc, 2, "parseImage", this);
            if (imgSrc.endsWith(".jpg")) {
                return imgSrc;
            }
            return "";
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

            return getBASE_URL() + url;
        } catch (ParserException e) {
            return "";
        }
    }
}
