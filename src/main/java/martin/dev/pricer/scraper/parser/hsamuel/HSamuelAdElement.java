package martin.dev.pricer.scraper.parser.hsamuel;

import martin.dev.pricer.scraper.parser.AdElementParser;
import org.jsoup.nodes.Element;

import java.util.HashMap;

public class HSamuelAdElement implements AdElementParser {

    private Element adInHtml;

    public HSamuelAdElement(Element adInHtml) {
        this.adInHtml = adInHtml;
    }

    @Override
    public String parseTitle() {
        return adInHtml.select("p[class=product-tile__description]").text();
    }

    @Override
    public String parseUpc() {
        Element skuElement = adInHtml.selectFirst("meta");
        return skuElement.attr("content");
    }

    @Override
    public String parsePrice() {
        String priceString = adInHtml.select("p[class*=current-price]").text();
        priceString = priceString.replaceAll("[^\\d.]", "");
        return priceString;
    }

    @Override
    public String parseImage() {
        Element imgElement = adInHtml.selectFirst("img[class^=product-tile__image]");
        return imgElement.attr("data-src");
    }

    @Override
    public String parseUrl() {
        Element element = adInHtml.select("a").first();
        String urlBase = "https://www.hsamuel.co.uk";
        return urlBase + element.attr("href");
    }

    @Override
    public HashMap<String, String> parseAll() {
        HashMap<String, String> dataMap = new HashMap<>();
        dataMap.put("title", parseTitle());
        dataMap.put("url", parseUrl());
        dataMap.put("img", parseImage());
        dataMap.put("upc", parseUpc());
        dataMap.put("price", parsePrice());
        return dataMap;
    }


}
