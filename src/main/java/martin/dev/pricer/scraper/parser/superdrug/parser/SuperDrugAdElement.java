package martin.dev.pricer.scraper.parser.superdrug.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import martin.dev.pricer.scraper.parser.AdElementParserImpl;
import org.jsoup.nodes.Element;

@Slf4j
public class SuperDrugAdElement extends AdElementParserImpl {

    public SuperDrugAdElement(Element adInHtml) {
        super(adInHtml);
    }

    @Override
    public String parseTitle() {
        Element titleElement = getAdInHtml().selectFirst("a[class*=item__productName]");
        return titleElement.text();
    }

    @Override
    public String parseUpc() {
        String urlString = parseUrl();
        String[] urlSplit = urlString.split("/p/");
        return "SD_" + urlSplit[1];
    }

    @Override
    public Double parsePrice() {
        String priceString = getAdInHtml().selectFirst("span[class*=item__price--now]").text();
        priceString = priceString.replaceAll("[^\\d.]", "");
        return Double.parseDouble(priceString);
    }

    @Override
    public String parseImage() {
        Element imgElement = getAdInHtml().selectFirst("img");
        String urlBase = "https://www.superdrug.com/";
        if (!imgElement.attr("src").equals("")) {
            return urlBase + imgElement.attr("src");
        } else {
            return urlBase + imgElement.attr("data-src");
        }
    }

    @Override
    public String parseUrl() {
        Element titleElement = getAdInHtml().selectFirst("a[class*=item__productName]");
        String urlBase = "https://www.superdrug.com/";
        return urlBase + titleElement.attr("href");
    }

    @Override
    public ParsedItemDto parseAll() {
        ParsedItemDto parsedItemDto = new ParsedItemDto(parseTitle(), parseUrl(), parseImage(), parseUpc(), parsePrice());
        log.info(parsedItemDto.toString());
        return parsedItemDto;
    }
}
