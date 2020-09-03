package martin.dev.pricer.state.scrapers;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.AbstractParser;
import martin.dev.pricer.scraper.ParserException;
import martin.dev.pricer.scraper.ParserValidator;
import martin.dev.pricer.state.ScraperParser;
import martin.dev.pricer.state.ScraperTools;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class TicWatchesParser implements ScraperParser {
    @Override
    public Elements parseListOfAdElements(Document document) {
        try {
            Elements parsedElements = document.select("li[class^=col]");
            Validate.notNull(parsedElements, "Elements should not be null");
            return parsedElements;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public String parseAdTitle(Element adInJsoupHtml) {
        try {
            Element titleElement = adInJsoupHtml.selectFirst("a");
            String title = titleElement.attr("title");
            return title;
        } catch (IllegalArgumentException e) {
            return "";
        }
    }

    @Override
    public String parseAdUpc(Element adInJsoupHtml) {
        try {
            Element upcElement = adInJsoupHtml.selectFirst("div");
            String upc = upcElement.attr("data-infid");
            return "TIC_" + upc;
        } catch (IllegalArgumentException e) {
            return "";
        }
    }

    @Override
    public Double parseAdPrice(Element adInJsoupHtml) {
        try {
            Element priceElement = adInJsoupHtml.selectFirst("span[class=product-content__price--inc]");
            String priceString = priceElement.text();
            Double price = ScraperTools.parseDoubleFromString(priceString);
            return price;
        } catch (IllegalArgumentException e) {
            return 0.0;
        }
    }

    @Override
    public String parseAdImage(Element adInJsoupHtml) {
        try {
            Element imgElement = adInJsoupHtml.selectFirst("img");
            String imgSrc = imgElement.attr("abs:data-src");
            return imgSrc;
        } catch (IllegalArgumentException e) {
            return "";
        }
    }

    @Override
    public String parseAdUrl(Element adInJsoupHtml) {
        try {
            Element aElement = adInJsoupHtml.selectFirst("a");
            String url = aElement.attr("href");
            return url;
        } catch (IllegalArgumentException e) {
            return "";
        }
    }

    @Override
    public boolean nextPageAvailable(Document document) {
        Element element = document.selectFirst("a[class=next-page page-arrow page_num ico icon-right]");
        return element != null && element.attr("href").length() > 1;
    }
}
