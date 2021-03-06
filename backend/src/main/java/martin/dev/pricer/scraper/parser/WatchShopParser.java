package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.Parser;
import martin.dev.pricer.scraper.Scraper;
import martin.dev.pricer.scraper.ScraperTools;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class WatchShopParser implements Parser {
    @Override
    public void parseListOfAdElements(Scraper scraper) {
        try {
            Elements parsedElements = scraper.getPageHtmlDocument().select("div[class*=product-container]");
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
            Element titleElement = adInJsoupHtml.selectFirst("meta[itemprop=name]");
            String title = titleElement.attr("content").trim();
            return title;
        } catch (NullPointerException e) {
            return "";
        }
    }

    @Override
    public String parseAdUpc(Element adInJsoupHtml) {
        try {
            Element upcElement = adInJsoupHtml.selectFirst("meta[itemprop=sku]");
            String upc = upcElement.attr("content");
            return "WS_" + upc;
        } catch (NullPointerException e) {
            return "";
        }
    }

    @Override
    public Double parseAdPrice(Element adInJsoupHtml) {
        try {
            Element priceElement = adInJsoupHtml.selectFirst("div[class=product-price]");
            priceElement = priceElement.selectFirst("strong");
            String priceString = priceElement.text();
            Double price = ScraperTools.parseDoubleFromString(priceString);
            return price;
        } catch (NullPointerException e) {
            return 0.0;
        }
    }

    @Override
    public String parseAdImage(Element adInJsoupHtml) {
        try {
            Element imgElement = adInJsoupHtml.selectFirst("div[class=product-img]");
            imgElement = imgElement.selectFirst("img");
            String imgSrc = imgElement.attr("src");
            if (imgSrc.endsWith("loader_border.gif")) {
                return "";
            }
            return imgSrc;
        } catch (NullPointerException e) {
            return "";
        }
    }

    @Override
    public String parseAdUrl(Element adInJsoupHtml) {
        try {
            Element imgElement = adInJsoupHtml.selectFirst("div[class=product-img]");
            imgElement = imgElement.selectFirst("a");
            String url = imgElement.attr("abs:href");

            return url;
        } catch (NullPointerException e) {
            return "";
        }
    }

    @Override
    public boolean nextPageAvailable(Document document) {
        Element element = document.selectFirst("div[class=controls-top]");
        return element.childNodes().toString().contains("title=\"Next page\"");
    }

    @Override
    public void nextPageUrl(Scraper scraper) {
        String[] x = scraper.getCurrentPageUrl().split("page=");
        int pageNumber = ScraperTools.parseIntegerFromString(x[1]) + 1;
        scraper.setCurrentPageNumber(pageNumber);
        scraper.setCurrentPageUrl(x[0] + "page=" + pageNumber);
    }
}
