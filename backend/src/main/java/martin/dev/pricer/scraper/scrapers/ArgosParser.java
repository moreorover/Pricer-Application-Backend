package martin.dev.pricer.scraper.scrapers;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.Parser;
import martin.dev.pricer.scraper.Scraper;
import martin.dev.pricer.scraper.ScraperTools;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

@Slf4j
public class ArgosParser implements Parser {
    @Override
    public void parseListOfAdElements(Scraper scraper) {
        try {
            Elements parsedElements = scraper.getPageHtmlDocument().select("div[class^=ProductCardstyles__Wrapper-]");
            Validate.notNull(parsedElements, "Elements should not be null");
            scraper.setAds(parsedElements);
        } catch (NullPointerException e) {
            log.error(e.getMessage());
            scraper.setAds(new Elements());
        }
    }

    @Override
    public String parseAdTitle(Element adInJsoupHtml) {
        try {
            Element titleElement = adInJsoupHtml.selectFirst("a[class*=Title]");
            String title = titleElement.text();
            return title;
        } catch (NullPointerException e) {
            return "";
        }
    }

    @Override
    public String parseAdUpc(Element adInJsoupHtml) {
        try {
            String upc = adInJsoupHtml.attr("data-product-id");
            return "A_" + upc;
        } catch (NullPointerException e) {
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
        } catch (NullPointerException e) {
            return 0.0;
        }
    }

    @Override
    public String parseAdImage(Element adInJsoupHtml) {
        //TODO webclient needs JS support to render image
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
            String upc = adInJsoupHtml.attr("data-product-id");
            return "https://www.argos.co.uk/product/" + upc;
        } catch (NullPointerException e) {
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
