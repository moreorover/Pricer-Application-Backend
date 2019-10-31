package martin.dev.pricer.scraper.parser.hsamuel.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import martin.dev.pricer.scraper.parser.AdElementParser;
import org.jsoup.nodes.Element;

@Slf4j
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
        Element upcElement = adInHtml.selectFirst("meta");
        return "HS_" + upcElement.attr("content");
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
        String urlBase = "https://www.hsamuel.co.uk";
        return urlBase + element.attr("href");
    }

    @Override
    public ParsedItemDto parseAll() {
        ParsedItemDto parsedItemDto = new ParsedItemDto(parseTitle(), parseUrl(), parseImage(), parseUpc(), parsePrice());
        log.info(parsedItemDto.toString());
        return parsedItemDto;
    }


}
