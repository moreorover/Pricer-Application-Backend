package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.AbstractParser;
import martin.dev.pricer.scraper.ParserException;
import martin.dev.pricer.scraper.ParserValidator;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class TicWatchesParser extends AbstractParser {

    public TicWatchesParser(ParserValidator parserValidator) {
        super(parserValidator, "Tic Watches", "TIC_", "https://www.ticwatches.co.uk/", 24, 1);
    }

    @Override
    public String makeNextPageUrl(int pageNum) {
        if (pageNum == 1) {
            return getUrlObject().getUrl();
        }
        String[] x = getUrlObject().getUrl().split("\\?page=");
        return x[0] + "?page=" + pageNum;
    }

    @Override
    public void parseListOfAdElements() {
        try {
        Elements parsedElements = getDocument().select("li[class^=col]");
        getParserValidator().validate(parsedElements, 1, "parseListOfAdElements", this);
        setElements(parsedElements);
    } catch (ParserException e) {
        setElements(new Elements());
    }
    }

    @Override
    public void parseMaxPageNum() {
        try {
            Element paginationBlockElement = getDocument().selectFirst("div[class=product-listings__top__view-all]");
            getParserValidator().validate(paginationBlockElement, 1, "parseMaxPageNum", this);
            String countString = paginationBlockElement.text();
            getParserValidator().validate(countString, 2, "parseMaxPageNum", this);
            Integer adsCount = parseIntegerFromString(countString);
            getParserValidator().validate(adsCount, 3, "parseMaxPageNum", this);
            Integer maxPageNum = calculateTotalPages(adsCount);
            getParserValidator().validate(maxPageNum, 4, "parseMaxPageNum", this);
            setMAX_PAGE_NUMBER(maxPageNum);
            log.info("Found " + adsCount + "ads to scrape, a total of " + maxPageNum + " pages.");
        } catch (ParserException e) {
            setMAX_PAGE_NUMBER(0);
        }

    }

    @Override
    public String parseTitle(Element adInJsoupHtml) {
    try {
        Element titleElement = adInJsoupHtml.selectFirst("a");
        getParserValidator().validate(titleElement, 1, "parseTitle", this);
        String title = titleElement.attr("title");
        getParserValidator().validate(title, 2, "parseTitle", this);

        return title;
    } catch (ParserException e) {
        return "";
        }
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) {
    try{
        Element upcElement = adInJsoupHtml.selectFirst("div");
        getParserValidator().validate(upcElement, 1, "parseUpc", this);
        String upc = upcElement.attr("data-infid");
        getParserValidator().validate(upc, 2, "parseUpc", this);

        return getPREFIX() + upc;
        } catch (ParserException e) {
        return "";
        }
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) {
    try{
        Element priceElement = adInJsoupHtml.selectFirst("span[class=product-content__price--inc]");
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
    try{
        Element imgElement = adInJsoupHtml.selectFirst("img");
        getParserValidator().validate(imgElement, 1, "parseImage", this);
        String imgSrc = imgElement.attr("data-src");
        getParserValidator().validate(imgSrc, 2, "parseImage", this);

        return getBASE_URL() + imgSrc;
        } catch (ParserException e) {
        return "";
        }
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) {
    try{
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
