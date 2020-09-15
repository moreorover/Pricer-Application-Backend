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
public class SimpkinsJewellersParser implements Parser {
    @Override
    public void parseListOfAdElements(Scraper scraper) {
        try {
            Elements parsedElements = scraper.getPageHtmlDocument().select("div.product.clearfix.product-hover");
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
            Element imageDiv = adInJsoupHtml.selectFirst("div[class=image]");
            String title = imageDiv.selectFirst("img").attr("alt");
            return title;
        } catch (NullPointerException e) {
            return "";
        }
    }

    @Override
    public String parseAdUpc(Element adInJsoupHtml) {
        try {
            Element hoverElement = adInJsoupHtml.selectFirst("div[class=only-hover]").selectFirst("a");
            String upcString = hoverElement.attr("onclick");
            String upc = ScraperTools.parseIntegerFromString(upcString).toString();
            return "SJ_" + upc;
        } catch (NullPointerException e) {
            return "";
        }
    }

    @Override
    public Double parseAdPrice(Element adInJsoupHtml) {
        try {
            String priceText = null;
            Element priceElement = adInJsoupHtml.selectFirst("div[class=price]");
            Elements priceElements = priceElement.children();
            if (priceElements.size() == 0) {
                priceText = priceElement.text();
            } else if (priceElements.size() == 2) {
                Element priceEl = priceElement.selectFirst("span[class=price-new]");
                priceText = priceEl.text();
            }
            Double price = ScraperTools.parseDoubleFromString(priceText);

            return price;
        } catch (NullPointerException e) {
            return 0.0;
        }
    }

    @Override
    public String parseAdImage(Element adInJsoupHtml) {
        try {
            Element imageDiv = adInJsoupHtml.selectFirst("div[class=image]").selectFirst("img");
            String imgUrl = imageDiv.attr("src");
            return imgUrl;
        } catch (NullPointerException e) {
            return "";
        }
    }

    @Override
    public String parseAdUrl(Element adInJsoupHtml) {
        try {
            Element imageDiv = adInJsoupHtml.selectFirst("div[class=image]").selectFirst("a");
            String url = imageDiv.attr("href");
            return url;
        } catch (NullPointerException e) {
            return "";
        }
    }

    @Override
    public boolean nextPageAvailable(Document document) {
        Element element = document.selectFirst("ul[class=pagination]");
        if (element == null) {
            return false;
        }
        return !element.childNodes().get(element.childNodeSize() - 1).attr("class").equals("active");
    }

    @Override
    public void nextPageUrl(Scraper scraper) {
        String[] x = scraper.getCurrentPageUrl().split("page=");
        int pageNumber = ScraperTools.parseIntegerFromString(x[1]) + 1;
        scraper.setCurrentPageNumber(pageNumber);
        scraper.setCurrentPageUrl(x[0] + "page=" + pageNumber);
    }
}
