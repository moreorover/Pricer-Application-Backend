package martin.dev.pricer.scraper.scrapers;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.Parser;
import martin.dev.pricer.scraper.Scraper;
import martin.dev.pricer.scraper.ScraperTools;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class HSamuelParser implements Parser {
    @Override
    public void parseListOfAdElements(Scraper scraper) {
        try {
            Elements parsedElements = scraper.getPageHtmlDocument().select("li[class^=product-tile-list__item]");
            Validate.notNull(parsedElements, "Elements should not be null");
            scraper.setAds(parsedElements);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public String parseAdTitle(Element adInJsoupHtml) {
        try {
            Element titleElement = adInJsoupHtml.selectFirst("p[class=product-tile__description]");
            String title = titleElement.text();
            return title;
        } catch (IllegalArgumentException e) {
            return "";
        }
    }

    @Override
    public String parseAdUpc(Element adInJsoupHtml) {
        try {
            String url = parseAdUrl(adInJsoupHtml);
            String[] strings = url.split("/d/");
            strings = strings[1].split("/");
            String upc = strings[0];

            return "HS_" + upc;
        } catch (IllegalArgumentException e) {
            return "";
        }

    }

    @Override
    public Double parseAdPrice(Element adInJsoupHtml) {
        try {
            Element priceElement = adInJsoupHtml.selectFirst("p[class*=current-price]");
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
            // adInJsoupHtml.select("noscript").select("img").attr("src")

            Element imgElement = adInJsoupHtml.selectFirst("noscript");
            imgElement = imgElement.selectFirst("img");
            String imgUrl = imgElement.attr("src");
            return imgUrl;
        } catch (IllegalArgumentException e) {
            return "";
        }
    }

    @Override
    public String parseAdUrl(Element adInJsoupHtml) {
        try {
            Element aElement = adInJsoupHtml.select("a").first();
            String url = aElement.attr("href");

            return "https://www.hsamuel.co.uk" + url;
        } catch (IllegalArgumentException e) {
            return "";
        }
    }

    @Override
    public boolean nextPageAvailable(Document document) {
        Element element = document.selectFirst("div[class=browse__pagination-and-infinity-scroll]");
        return element.childNodes().toString().contains("rel=\"next\"");
    }
}
