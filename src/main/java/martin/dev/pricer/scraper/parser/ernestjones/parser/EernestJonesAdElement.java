package martin.dev.pricer.scraper.parser.ernestjones.parser;

import martin.dev.pricer.scraper.model.ParsedItemDto;
import martin.dev.pricer.scraper.parser.AdElementParser;
import org.jsoup.nodes.Element;

public class EernestJonesAdElement implements AdElementParser {

    private Element adInHtml;

    public EernestJonesAdElement(Element adInHtml) {
        this.adInHtml = adInHtml;
    }

    @Override
    public String parseTitle() {
        return adInHtml.select("p[class=product-tile__description]").text();
    }

    @Override
    public String parseUpc() {
        Element skuElement = adInHtml.selectFirst("meta");
        return "EJ_" + skuElement.attr("content");
    }

    @Override
    public Double parsePrice() {
        String priceString = adInHtml.select("p[class*=current-price]").text();
        priceString = priceString.replaceAll("[^\\d.]", "");
        return Double.parseDouble(priceString);
    }

    @Override
    public String parseImage() {
        Element imgElement = adInHtml.selectFirst("img[class^=product-tile__image]");
        return imgElement.attr("data-src");
    }

    @Override
    public String parseUrl() {
        Element element = adInHtml.select("a").first();
        String urlBase = "https://www.ernestjones.co.uk";
        return urlBase + element.attr("href");
    }

    @Override
    public ParsedItemDto parseAll() {
        return new ParsedItemDto(parseTitle(), parseUrl(), parseImage(), parseUpc(), parsePrice());
    }


}
