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
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

@Slf4j
public class ArgosParser implements ScraperParser {
    @Override
    public Elements parseListOfAdElements(Document document) {
        try {
            Elements parsedElements = document.select("div[class^=ProductCardstyles__Wrapper-]");
            Validate.notNull(parsedElements, "Elements should not be null");
            return parsedElements;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public String parseAdTitle(Element adInJsoupHtml) {
        try {
            Element titleElement = adInJsoupHtml.selectFirst("a[class*=Title]");
            String title = titleElement.text();
            return title;
        } catch (IllegalArgumentException e) {
            return "";
        }
    }

    @Override
    public String parseAdUpc(Element adInJsoupHtml) {
        try {
            String upc = adInJsoupHtml.attr("data-product-id");
            return "A_" + upc;
        } catch (IllegalArgumentException e) {
            return "";
        }
    }

    @Override
    public Double parseAdPrice(Element adInJsoupHtml) {
        try {
            Element priceElement = adInJsoupHtml.selectFirst("div[class*=PriceText]");
            String priceString = priceElement.text();
            Double price = ScraperTools.parseDoubleFromString(priceString);
            return price;
        } catch (IllegalArgumentException e) {
            return 0.0;
        }
    }

    @Override
    public String parseAdImage(Element adInJsoupHtml) {
//        try {
//            Element imgElement = adInJsoupHtml.selectFirst("div[class*=ImageWrapper]");
//            imgElement = imgElement.selectFirst("picture");
//            imgElement = imgElement.selectFirst("img");
//            String imgUrl = imgElement.attr("src");
//            return imgUrl;
//        } catch (IllegalArgumentException e) {
//            return "";
//        }
        return "";
    }

    @Override
    public String parseAdUrl(Element adInJsoupHtml) {
        try {
            Element urlElement = adInJsoupHtml.selectFirst("a");
            String url = urlElement.attr("abs:href");
            return url;
        } catch (IllegalArgumentException e) {
            return "";
        }
    }

    @Override
    public boolean nextPageAvailable(Document document) {
        Element element = document.selectFirst("nav[aria-label=Pagination Navigation]");
        if (element.childNodeSize() == 1) {
            return false;
        }
        Node e = element.child(element.childNodeSize() - 1);
        return !e.outerHtml().contains("div disabled class=");
    }
}
