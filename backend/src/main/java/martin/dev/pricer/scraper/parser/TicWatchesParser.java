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
public class TicWatchesParser implements Parser {
    @Override
    public void parseListOfAdElements(Scraper scraper) {
        try {
            Elements parsedElements = scraper.getPageHtmlDocument().select("li[class^=col]");
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
            Element titleElement = adInJsoupHtml.selectFirst("a");
            String title = titleElement.attr("title");
            return title;
        } catch (NullPointerException e) {
            return "";
        }
    }

    @Override
    public String parseAdUpc(Element adInJsoupHtml) {
        try {
            Element upcElement = adInJsoupHtml.selectFirst("div");
            String upc = upcElement.attr("data-infid");
            return "TIC_" + upc;
        } catch (NullPointerException e) {
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
        } catch (NullPointerException e) {
            return 0.0;
        }
    }

    @Override
    public String parseAdImage(Element adInJsoupHtml) {
        try {
            Element imgElement = adInJsoupHtml.selectFirst("img");
            String imgSrc = imgElement.attr("abs:data-src");
            return imgSrc;
        } catch (NullPointerException e) {
            return "";
        }
    }

    @Override
    public String parseAdUrl(Element adInJsoupHtml) {
        try {
            Element aElement = adInJsoupHtml.selectFirst("a");
            String url = aElement.attr("href");
            return url;
        } catch (NullPointerException e) {
            return "";
        }
    }

    @Override
    public boolean nextPageAvailable(Document document) {
        Element element = document.selectFirst("a[class=next-page page-arrow page_num ico icon-right]");
        return element != null && element.attr("href").length() > 1;
    }

    @Override
    public void nextPageUrl(Scraper scraper) {
        // https://www.ticwatches.co.uk/womens-watches-c2?page=1
        String[] x = scraper.getCurrentPageUrl().split("page=");
        int pageNumber = ScraperTools.parseIntegerFromString(x[1]) + 1;
        scraper.setCurrentPageNumber(pageNumber);
        scraper.setCurrentPageUrl(x[0] + "page=" + pageNumber);
    }
}
