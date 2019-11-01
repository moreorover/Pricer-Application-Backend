package martin.dev.pricer.scraper.parser.argos.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import martin.dev.pricer.scraper.parser.AdElementParserImpl;
import org.jsoup.nodes.Element;

@Slf4j
public class ArgosAdElement extends AdElementParserImpl {

    public ArgosAdElement(Element adInHtml) {
        super(adInHtml);
    }

    @Override
    public String parseTitle() {
        Element titleElement = getAdInHtml().selectFirst("a[class*=Title]");
        return titleElement.text();
    }

    @Override
    public String parseUpc() {
        Element upcElement = getAdInHtml();
        return "A_" + upcElement.attr("data-product-id");
    }

    @Override
    public Double parsePrice() {
        String priceString = getAdInHtml().selectFirst("div[class*=PriceText]").text();
        priceString = priceString.replaceAll("[^\\d.]", "");
        return Double.parseDouble(priceString);
    }

    @Override
    public String parseImage() {
        Element imgElement = getAdInHtml().selectFirst("div[class*=ImageWrapper]");
        imgElement = imgElement.selectFirst("picture");
        imgElement = imgElement.selectFirst("img");
        return imgElement.attr("src");
    }

    @Override
    public String parseUrl() {
        Element urlElement = getAdInHtml().selectFirst("a");
        String urlBase = "https://www.argos.co.uk";
        return urlBase + urlElement.attr("href");
    }

    @Override
    public ParsedItemDto parseAll() {
        ParsedItemDto parsedItemDto = new ParsedItemDto(parseTitle(), parseUrl(), parseImage(), parseUpc(), parsePrice());
        log.info(parsedItemDto.toString());
        return parsedItemDto;
    }
}
