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
public class CreationWatchesParser implements Parser {

    @Override
    public void parseListOfAdElements(Scraper scraper) {
        try {
            Elements parsedElements = scraper.getPageHtmlDocument().select("div[class=product-box]");
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
            Element titleElement = adInJsoupHtml.selectFirst("h3[class=product-name]").selectFirst("a");
            String title = titleElement.text();
            return title;
        } catch (NullPointerException e) {
            return "";
        }
    }

    @Override
    public String parseAdUpc(Element adInJsoupHtml) {
        try {
            Element modelElement = adInJsoupHtml.selectFirst("p[class=product-model-no]");
            String upcText = modelElement.text();
            String[] stringArray = upcText.split(": ");
            String upc = stringArray[1];

            return "CW_" + upc;
        } catch (NullPointerException e) {
            return "";
        }
    }

    @Override
    public Double parseAdPrice(Element adInJsoupHtml) {
        try {
            Element titleElement = adInJsoupHtml.selectFirst("p[class=product-price]").selectFirst("span");
            String priceString = titleElement.text();
            Double price = ScraperTools.parseDoubleFromString(priceString);

            return price;
        } catch (NullPointerException e) {
            return 0.0;
        }
    }

    @Override
    public String parseAdImage(Element adInJsoupHtml) {
        try {
            Element titleElement = adInJsoupHtml.selectFirst("div[class=product-img-box]").selectFirst("img");
            String imgUrl = titleElement.attr("src");

            return imgUrl;
        } catch (NullPointerException e) {
            return "";
        }
    }

    @Override
    public String parseAdUrl(Element adInJsoupHtml) {
        try {
            Element titleElement = adInJsoupHtml.selectFirst("h3[class=product-name]").selectFirst("a");
            String url = titleElement.attr("href");

            return url;
        } catch (NullPointerException e) {
            return "";
        }
    }

    @Override
    public boolean nextPageAvailable(Document document) {
        Element element = document.selectFirst("div[class=fr pagina]");
        return element.childNodes().toString().contains("Next Page");
    }
}
