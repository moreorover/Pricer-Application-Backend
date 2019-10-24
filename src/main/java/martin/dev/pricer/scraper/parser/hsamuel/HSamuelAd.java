package martin.dev.pricer.scraper.parser.hsamuel;

import martin.dev.pricer.scraper.parser.MainAdParser;
import org.jsoup.nodes.Element;

public class HSamuelAd implements MainAdParser {

    private Element adInHtml;

    public HSamuelAd(Element adInHtml) {
        this.adInHtml = adInHtml;
    }

    @Override
    public String parseTitle() {
        return adInHtml.select("p[class=product-tile__description]").text();
    }

    @Override
    public String parseUpc() {
        return null;
    }

    @Override
    public Double parsePrice() {
        //String priceString = adInHtml.select("p[class^=product-tile__current-price").text();
        String priceString = adInHtml.select("div[class=product-tile__pricing-container").text();
        priceString = priceString.replaceAll("[^\\d.]", "");
        return Double.parseDouble(priceString);
    }

    @Override
    public String parseImage() {
        return null;
    }

    @Override
    public String parseUrl() {
        Element element = adInHtml.select("a").first();
        return element.attr("href");
    }
}
