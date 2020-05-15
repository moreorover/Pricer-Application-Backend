package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.AbstractParser;
import martin.dev.pricer.scraper.ParserException;
import martin.dev.pricer.scraper.ParserValidator;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class HSamuelParser extends AbstractParser {

    public HSamuelParser(ParserValidator parserValidator) {
        super(parserValidator, "H. Samuel", "HS_", "https://www.hsamuel.co.uk", 24, 1);
    }

    @Override
    public String makeNextPageUrl(int pageNum) {
        String[] x = getUrlObject().getUrl().split("Pg=");
        return x[0] + "Pg=" + pageNum;
    }

    @Override
    public void parseListOfAdElements() {
        try {
            Elements parsedElements = getDocument().select("li[class^=product-tile-list__item]");
            getParserValidator().validate(parsedElements, 1, "parseListOfAdElements", this);
            setElements(parsedElements);
        } catch (ParserException e) {
            setElements(new Elements());
        }
    }

    @Override
    public void parseMaxPageNum() {
        try {
            Element paginationBlockElement = getDocument().selectFirst("ol[class*=pageNumbers]");
            getParserValidator().validate(paginationBlockElement, 1, "parseMaxPageNum", this);
            Elements paginationButtons = paginationBlockElement.select("li");
            getParserValidator().validate(paginationButtons, 2, "parseMaxPageNum", this);
            Element lastPageElement = paginationButtons.get(5);
            getParserValidator().validate(lastPageElement, 3, "parseMaxPageNum", this);
            String lastPageText = lastPageElement.text();
            getParserValidator().validate(lastPageText, 4, "parseMaxPageNum", this);
            int maxPageNum = parseIntegerFromString(lastPageText);
            getParserValidator().validate(maxPageNum, 5, "parseMaxPageNum", this);
            setMAX_PAGE_NUMBER(maxPageNum);
            log.info("Found " + "? " + "ads to scrape, a total of " + maxPageNum + " pages.");
        } catch (ParserException e) {
            setMAX_PAGE_NUMBER(0);
        }
    }

    @Override
    public String parseTitle(Element adInJsoupHtml) {
        try {
            Element titleElement = adInJsoupHtml.selectFirst("p[class=product-tile__description]");
            getParserValidator().validate(titleElement, 1, "parseTitle", this);
            String title = titleElement.text();
            getParserValidator().validate(titleElement, 2, "parseTitle", this);

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
            String[] strings = url.split("/d/");
            getParserValidator().validate(strings, 2, "parseUpc", this);
            strings = strings[1].split("/");
            getParserValidator().validate(strings, 3, "parseUpc", this);
            String upc = strings[0];
            getParserValidator().validate(upc, 4, "parseUpc", this);

            return getPREFIX() + upc;
        } catch (ParserException e) {
            return "";
        }

    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) {
        try {
            Element priceElement = adInJsoupHtml.selectFirst("p[class*=current-price]");
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
            // adInJsoupHtml.select("noscript").select("img").attr("src")

            Element imgElement = adInJsoupHtml.selectFirst("noscript");
            getParserValidator().validate(imgElement, 1, "parseImage", this);
            imgElement = imgElement.selectFirst("img");
            getParserValidator().validate(imgElement, 2, "parseImage", this);
            String imgUrl = imgElement.attr("src");
            getParserValidator().validate(imgUrl, 3, "parseImage", this);
            return imgUrl;
        } catch (ParserException e) {
            return "";
        }
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) {
        try {
            Element aElement = adInJsoupHtml.select("a").first();
            getParserValidator().validate(aElement, 1, "parseUrl", this);
            String url = aElement.attr("href");
            getParserValidator().validate(url, 2, "parseUrl", this);

            return getBASE_URL() + url;
        } catch (ParserException e) {
            return "";
        }
    }
}
