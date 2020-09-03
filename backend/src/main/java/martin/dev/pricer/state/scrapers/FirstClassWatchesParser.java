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
public class FirstClassWatchesParser implements ScraperParser {

    @Override
    public Elements parseListOfAdElements(Document document) {
        try {
            Elements parsedElements = document.select("a[class=listingproduct]");
            Validate.notNull(parsedElements, "Elements should not be null");
            return parsedElements;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public String parseAdTitle(Element adInJsoupHtml) {
        try {
            String title = adInJsoupHtml.attr("title").trim();
            return title;
        } catch (IllegalArgumentException e) {
            return "";
        }
    }

    @Override
    public String parseAdUpc(Element adInJsoupHtml) {
        try {
            String upc = adInJsoupHtml.attr("data-id");
            return "FCW_" + upc;
        } catch (IllegalArgumentException e) {
            return "";
        }
    }

    @Override
    public Double parseAdPrice(Element adInJsoupHtml) {
        try {
            String priceString = adInJsoupHtml.attr("data-price");
            Double price = ScraperTools.parseDoubleFromString(priceString);

            return price;
        } catch (IllegalArgumentException e) {
            return 0.0;
        }
    }

    @Override
    public String parseAdImage(Element adInJsoupHtml) {
        try {
            Element imgElement = adInJsoupHtml.selectFirst("div[class=image]").selectFirst("img");
            String imgUrl = imgElement.attr("src");
            if (imgUrl.endsWith("loader_border.gif")) {
                return "";
            }
            return imgUrl;
        } catch (IllegalArgumentException e) {
            return "";
        }
    }

    @Override
    public String parseAdUrl(Element adInJsoupHtml) {
        try {
            String url = adInJsoupHtml.attr("href");
            return url;
        } catch (IllegalArgumentException e) {
            return "";
        }
    }

    @Override
    public boolean nextPageAvailable(Document document) {
        Element element = document.selectFirst("div[class=listingpagination]");
        return element.childNodes().toString().contains("class=\"next\"");
    }
}
