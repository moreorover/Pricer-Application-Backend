package martin.dev.pricer.scraper.parser.debenhams;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import martin.dev.pricer.scraper.parser.Parser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DebenhamsParser implements Parser {

    @Override
    public Elements parseListOfAdElements(Document pageContentInJsoupHtml) {
        return pageContentInJsoupHtml.select("article[class^=c-product-item]");
    }

    @Override
    public int parseMaxPageNum(Document pageContentInJsoupHtml) {
        String countString = pageContentInJsoupHtml.selectFirst("div[class*=dbh-count]").text();
        countString = countString.replaceAll("[^\\d.]", "");
        int adsCount = Integer.parseInt(countString);
        return (adsCount + 60 - 1) / 60;
    }

    @Override
    public String parseTitle(Element adInJsoupHtml) {
        Element titleElement = adInJsoupHtml.selectFirst("div[class^=c-product-item-title]");
        return titleElement.text();
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) {
        Element aElement = adInJsoupHtml.selectFirst("a");
        return "DBH_" + aElement.attr("href").split("prod_")[1];
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) {
        Element priceElement = adInJsoupHtml.selectFirst("span[itemprop=price]");
        String priceString = priceElement.text().replaceAll("[^\\d.]", "");
        return Double.parseDouble(priceString);
    }

    @Override
    public String parseImage(Element adInJsoupHtml) {
        Element imgElement = adInJsoupHtml.selectFirst("img");
        return imgElement.attr("src");
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) {
        Element aElement = adInJsoupHtml.selectFirst("a");
        String urlBase = "https://www.debenhams.com";
        return urlBase + aElement.attr("href");
    }

    @Override
    public ParsedItemDto fetchItemDtoFromHtml(Element adInJsoupHtml) {
        log.info(adInJsoupHtml.outerHtml());
        String title = parseTitle(adInJsoupHtml);
        Double price = parsePrice(adInJsoupHtml);
//        String img = parseImage(adInJsoupHtml);
        String upc = parseUpc(adInJsoupHtml);
        String url = parseUrl(adInJsoupHtml);

        ParsedItemDto parsedItemDto = new ParsedItemDto(title, url, null, upc, price);

        log.info(parsedItemDto.toString());
        return parsedItemDto;
    }
}
