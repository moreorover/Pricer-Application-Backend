package martin.dev.pricer.scraper.parser.ernestjones.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import martin.dev.pricer.scraper.parser.AdElementParser;
import martin.dev.pricer.scraper.parser.AdElementParserImpl;
import org.jsoup.nodes.Element;

@Slf4j
public class ErnestJonesAdElement extends AdElementParserImpl {

    public ErnestJonesAdElement(Element adInHtml) {
        super(adInHtml);
    }

    @Override
    public String parseTitle() {
        return getAdInHtml().select("p[class=product-tile__description]").text();
    }

    @Override
    public String parseUpc() {
        Element skuElement = getAdInHtml().selectFirst("meta");
        return "EJ_" + skuElement.attr("content");
    }

    @Override
    public Double parsePrice() {
        String priceString = getAdInHtml().select("p[class*=current-price]").text();
        priceString = priceString.replaceAll("[^\\d.]", "");
        return Double.parseDouble(priceString);
    }

    @Override
    public String parseImage() {
        Element imgElement = getAdInHtml().selectFirst("img[class^=product-tile__image]");
        return imgElement.attr("data-src");
    }

    @Override
    public String parseUrl() {
        Element element = getAdInHtml().select("a").first();
        String urlBase = "https://www.ernestjones.co.uk";
        return urlBase + element.attr("href");
    }

    @Override
    public ParsedItemDto parseAll() {
        ParsedItemDto parsedItemDto = new ParsedItemDto(parseTitle(), parseUrl(), parseImage(), parseUpc(), parsePrice());
        log.info(parsedItemDto.toString());
        return parsedItemDto;
    }


}
