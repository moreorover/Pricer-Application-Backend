package martin.dev.pricer.state.scrapers;

import martin.dev.pricer.state.ScraperParser;
import martin.dev.pricer.state.ScraperTools;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CreationWatchesParser implements ScraperParser {

    @Override
    public Elements parseListOfAdElements(Document document) {
        try {
            Elements parsedElements = document.select("div[class=product-box]");
            Validate.notNull(parsedElements, "Elements should not be null");
            return parsedElements;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public String parseAdTitle(Element adInJsoupHtml) {
        try {
            Element titleElement = adInJsoupHtml.selectFirst("h3[class=product-name]").selectFirst("a");
            String title = titleElement.text();
            return title;
        } catch (IllegalArgumentException e) {
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
        } catch (IllegalArgumentException e) {
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
        } catch (IllegalArgumentException e) {
            return 0.0;
        }
    }

    @Override
    public String parseAdImage(Element adInJsoupHtml) {
        try {
            Element titleElement = adInJsoupHtml.selectFirst("div[class=product-img-box]").selectFirst("img");
            String imgUrl = titleElement.attr("src");

            return imgUrl;
        } catch (IllegalArgumentException e) {
            return "";
        }
    }

    @Override
    public String parseAdUrl(Element adInJsoupHtml) {
        try {
            Element titleElement = adInJsoupHtml.selectFirst("h3[class=product-name]").selectFirst("a");
            String url = titleElement.attr("href");

            return url;
        } catch (IllegalArgumentException e) {
            return "";
        }
    }

    @Override
    public boolean nextPageAvailable(Document document) {
        Element element = document.selectFirst("div[class=fr pagina]");
        return element.childNodes().toString().contains("Next Page");
    }
}
