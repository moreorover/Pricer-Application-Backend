package martin.dev.pricer.scraper.parser.superdrug.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.PricerApplication;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import martin.dev.pricer.scraper.parser.AdElementParser;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class SuperDrugAdElement implements AdElementParser {

    private Element adInHtml;

    private static final Logger logger = LoggerFactory.getLogger(PricerApplication.class);

    public SuperDrugAdElement(Element adInHtml) {
        this.adInHtml = adInHtml;
    }

    @Override
    public String parseTitle() {
        Element titleElement = adInHtml.selectFirst("a[class*=item__productName]");
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
        String priceString = adInHtml.selectFirst("span[class*=item__price--now]").text();
        priceString = priceString.replaceAll("[^\\d.]", "");
        return Double.parseDouble(priceString);
    }

    @Override
    public String parseImage() {
        Element imgElement = adInHtml.selectFirst("img");
        String urlBase = "https://www.superdrug.com/";
        if (!imgElement.attr("src").equals("")){
            return urlBase + imgElement.attr("src");
        } else {
            return urlBase + imgElement.attr("data-src");
        }
    }

    @Override
    public String parseUrl() {
        Element titleElement = adInHtml.selectFirst("a[class*=item__productName]");
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
