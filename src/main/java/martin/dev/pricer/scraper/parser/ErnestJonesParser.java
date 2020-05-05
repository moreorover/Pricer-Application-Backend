package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.AbstractParser;
import martin.dev.pricer.scraper.ParserException;
import martin.dev.pricer.scraper.ParserValidator;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class ErnestJonesParser extends AbstractParser {

    public ErnestJonesParser(ParserValidator parserValidator) {
        super(parserValidator, "Ernest Jones", "EJ_", "https://www.ernestjones.co.uk", 24, 1);
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
            getParserValidator().validate(parsedElements, 1, "parseListOfAdElements");
            setElements(parsedElements);
        } catch (
                ParserException e) {
            setElements(new Elements());
        }

    }

    @Override
    public void parseMaxPageNum() {
        try {
            Element paginationBlockElement = getDocument().selectFirst("ol[class*=pageNumbers]");
            getParserValidator().validate(paginationBlockElement, 1, "parseMaxPageNum");
            Elements paginationButtons = paginationBlockElement.select("li");
            getParserValidator().validate(paginationButtons, 2, "parseMaxPageNum");
            Element lastPageElement = paginationButtons.get(7);
            getParserValidator().validate(lastPageElement, 3, "parseMaxPageNum");
            String lastPageText = lastPageElement.text();
            getParserValidator().validate(lastPageText, 4, "parseMaxPageNum");
            int maxPageNum = parseIntegerFromString(lastPageText);
            getParserValidator().validate(maxPageNum, 5, "parseMaxPageNum");
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
            Element titleElement = adInJsoupHtml.selectFirst("p[class=product-tile__description]");
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
            String url = parseUrl(adInJsoupHtml);
            getParserValidator().validate(url, 1, "parseUpc");
            String[] strings = url.split("/d/");
            getParserValidator().validate(strings, 2, "parseUpc");
            strings = strings[1].split("/");
            getParserValidator().validate(strings, 3, "parseUpc");
            String upc = strings[0];
            getParserValidator().validate(upc, 4, "parseUpc");

            return getPREFIX() + upc;
        } catch (ParserException e) {
            return "";
        }
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) {
        try {
            Element priceElement = adInJsoupHtml.selectFirst("p[class*=current-price]");
            getParserValidator().validate(priceElement, 1, "parsePrice");
            String priceString = priceElement.text();
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
            Element imgElement = adInJsoupHtml.selectFirst("img[class^=product-tile__image]");
            getParserValidator().validate(imgElement, 1, "parseImage");
            String imgUrl = imgElement.attr("data-src");
            getParserValidator().validate(imgUrl, 2, "parseImage");

            return imgUrl;
        } catch (ParserException e) {
            return "";
        }
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) {
        try {
            Element aElement = adInJsoupHtml.select("a").first();
            getParserValidator().validate(aElement, 1, "parseUrl");
            String url = aElement.attr("href");
            getParserValidator().validate(url, 2, "parseUrl");

            return getBASE_URL() + url;
        } catch (ParserException e) {
            return "";
        }
    }
}
