package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.AbstractParser;
import martin.dev.pricer.scraper.ParserException;
import martin.dev.pricer.scraper.ParserValidator;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class SimpkinsJewellersParser extends AbstractParser {

    public SimpkinsJewellersParser(ParserValidator parserValidator) {
        super(parserValidator, "Simpkins Jewellers", "SJ_", "https://simpkinsjewellers.co.uk", 100, 1);
    }

    @Override
    public String makeNextPageUrl(int pageNum) {
        String[] x = getUrlObject().getUrl().split("page=");
        return x[0] + "page=" + pageNum;
    }

    @Override
    public void parseListOfAdElements() {
        try {
            Elements parsedElements = getDocument().select("div.product.clearfix.product-hover");
            getParserValidator().validate(parsedElements, 1, "parseListOfAdElements", this);
            setElements(parsedElements);
        } catch (ParserException e) {
            setElements(new Elements());
        }
    }

    @Override
    public void parseMaxPageNum() {
        try {
            Element paginationElement = getDocument().selectFirst("div.row.pagination-results");
            getParserValidator().validate(paginationElement, 1, "parseMaxPageNum", this);
            Elements paginationElements = paginationElement.children();
            getParserValidator().validate(paginationElements, 2, "parseMaxPageNum", this);
            Element lastPageElement = paginationElements.get(paginationElements.size() - 1);
            getParserValidator().validate(lastPageElement, 3, "parseMaxPageNum", this);
            String paginationText = lastPageElement.text().split(" \\(")[1];
            getParserValidator().validate(paginationText, 4, "parseMaxPageNum", this);
            String lastPageNumber = paginationText.replaceAll("[^\\d.]", "");
            getParserValidator().validate(lastPageNumber, 5, "parseMaxPageNum", this);
            int maxPageNum = Integer.parseInt(lastPageNumber);
            getParserValidator().validate(maxPageNum, 6, "parseMaxPageNum", this);
            setMAX_PAGE_NUMBER(maxPageNum);
            log.info("Found " + "? " + "ads to scrape, a total of " + maxPageNum + " pages.");
        } catch (ParserException e) {
            setMAX_PAGE_NUMBER(0);
        }

    }

    @Override
    public String parseTitle(Element adInJsoupHtml) {
        try {
            Element imageDiv = adInJsoupHtml.selectFirst("div[class=image]");
            getParserValidator().validate(imageDiv, 1, "parseTitle", this);
            String title = imageDiv.selectFirst("img").attr("alt");
            getParserValidator().validate(title, 2, "parseTitle", this);

            return title;
        } catch (ParserException e) {
            return "";
        }
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) {
        try {
            Element hoverElement = adInJsoupHtml.selectFirst("div[class=only-hover]").selectFirst("a");
            getParserValidator().validate(hoverElement, 1, "parseUpc", this);
            String upcString = hoverElement.attr("onclick");
            getParserValidator().validate(upcString, 2, "parseUpc", this);
            String upc = parseIntegerFromString(upcString).toString();
            getParserValidator().validate(upc, 3, "parseUpc", this);

            return getPREFIX() + upc;
        } catch (ParserException e) {
            return "";
        }
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) {
        try {
            String priceText = null;
            Element priceElement = adInJsoupHtml.selectFirst("div[class=price]");
            getParserValidator().validate(priceElement, 1, "parsePrice", this);
            Elements priceElements = priceElement.children();
            if (priceElements.size() == 0) {
                priceText = priceElement.text();
                getParserValidator().validate(priceText, 2, "parsePrice", this);
            } else if (priceElements.size() == 2) {
                Element priceEl = priceElement.selectFirst("span[class=price-new]");
                getParserValidator().validate(priceEl, 3, "parsePrice", this);
                priceText = priceEl.text();
                getParserValidator().validate(priceText, 4, "parsePrice", this);
            }
            getParserValidator().validate(priceText, 5, "parsePrice", this);
            Double price = parseDoubleFromString(priceText);
            getParserValidator().validate(price, 6, "parsePrice", this);

            return price;
        } catch (ParserException e) {
            return 0.0;
        }
    }

    @Override
    public String parseImage(Element adInJsoupHtml) {
        try {
            Element imageDiv = adInJsoupHtml.selectFirst("div[class=image]").selectFirst("img");
            getParserValidator().validate(imageDiv, 1, "parseImage", this);
            String imgUrl = imageDiv.attr("src");
            getParserValidator().validate(imgUrl, 2, "parseImage", this);
            return imgUrl;
        } catch (ParserException e) {
            return "";
        }
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) {
        try {
            Element imageDiv = adInJsoupHtml.selectFirst("div[class=image]").selectFirst("a");
            getParserValidator().validate(imageDiv, 1, "parseUrl", this);
            String url = imageDiv.attr("href");
            getParserValidator().validate(url, 2, "parseUrl", this);
            return url;
        } catch (ParserException e) {
            return "";
        }
    }
}
