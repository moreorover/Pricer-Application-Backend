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
public class DebenhamsParser implements Parser {
    @Override
    public void parseListOfAdElements(Scraper scraper) {
        try {
            Elements parsedElements = scraper.getPageHtmlDocument().select("div[class^=c-product-item]");
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
            Element titleElement = adInJsoupHtml.selectFirst("h2[class^=c-product-item-title]");
            String title = titleElement.text();
            return title;
        } catch (NullPointerException e) {
            return "";
        }
    }

    @Override
    public String parseAdUpc(Element adInJsoupHtml) {
        try {
            Element aElement = adInJsoupHtml.selectFirst("a");
            String upcText = aElement.attr("href");
            String[] upcTextArray = upcText.split("prod_");
            String upc = upcTextArray[1];

            return "DBH_" + upc;
        } catch (NullPointerException e) {
            return "";
        }
    }

    @Override
    public Double parseAdPrice(Element adInJsoupHtml) {
        try {
            Element priceElement = adInJsoupHtml.selectFirst("span[itemprop=price]");
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
            Element imgElement = adInJsoupHtml.selectFirst("div[class^=dbh-image]");
            imgElement = imgElement.selectFirst("img");
            String imgString = imgElement.attr("abs:src");
            return imgString;
        } catch (NullPointerException e) {
            return "";
        }
    }

    @Override
    public String parseAdUrl(Element adInJsoupHtml) {
        try {
            adInJsoupHtml.setBaseUri("https://www.debenhams.com");
            Element aElement = adInJsoupHtml.selectFirst("a");
            String url = aElement.attr("abs:href");
            return url;
        } catch (NullPointerException e) {
            return "";
        }
    }

    @Override
    public boolean nextPageAvailable(Document document) {
        Element element = document.selectFirst("div[class=c-product-control-bar__pagination-wrapper]");
        element = element.selectFirst("button[class*=pw-pagination__next]");
        return !element.outerHtml().contains("button disabled class");
    }

    @Override
    public void nextPageUrl(Scraper scraper) {
        // https://www.debenhams.com/men/accessories/watches?pn=1&?shipToCntry=GB
        String[] x = scraper.getCurrentPageUrl().split("pn=");
        String[] y = x[1].split("&?sh");
        int pageNumber = ScraperTools.parseIntegerFromString(y[0]) + 1;
        scraper.setCurrentPageNumber(pageNumber);
        scraper.setCurrentPageUrl(x[0] + "pn=" + pageNumber + "&?shipToCntry=GB");
    }
}
