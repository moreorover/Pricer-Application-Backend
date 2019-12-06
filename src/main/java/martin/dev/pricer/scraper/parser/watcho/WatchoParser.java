package martin.dev.pricer.scraper.parser.watcho;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import martin.dev.pricer.scraper.parser.Parser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WatchoParser implements Parser {

    @Override
    public Elements parseListOfAdElements(Document pageContentInJsoupHtml) {
        return pageContentInJsoupHtml.select("li[class=product]");
    }

    @Override
    public int parseMaxPageNum(Document pageContentInJsoupHtml) {
        Elements paginationElements = pageContentInJsoupHtml.select("li[class^=pagination-item]");
        if (paginationElements.size() == 0){
            return 1;
        }
        Element lastPaginationElement = paginationElements.get(paginationElements.size() - 2);
        return Integer.parseInt(lastPaginationElement.text());
    }

    @Override
    public String parseTitle(Element adInJsoupHtml){
        Element titleElement = adInJsoupHtml.selectFirst("h4[class=card-title]");
        return titleElement.text();
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) {
        return "WO_" + adInJsoupHtml.selectFirst("article").attr("data-entity-id");
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) {
        String priceString = adInJsoupHtml.selectFirst("span[class=price price--withTax]").text();
        priceString = priceString.replaceAll("[^\\d.]", "");
        return Double.parseDouble(priceString);
    }

    @Override
    public String parseImage(Element adInJsoupHtml) {
        Element imgElement = adInJsoupHtml.selectFirst("img[class^=card-image]");
        return imgElement.attr("data-src");
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) {
        Element urlElement = adInJsoupHtml.selectFirst("a");
        return urlElement.attr("href");
    }

    @Override
    public ParsedItemDto fetchItemDtoFromHtml(Element adInJsoupHtml) {
        String title = parseTitle(adInJsoupHtml);
        String upc = parseUpc(adInJsoupHtml);
        Double price = parsePrice(adInJsoupHtml);
        String img = parseImage(adInJsoupHtml);
        String url = parseUrl(adInJsoupHtml);

        ParsedItemDto parsedItemDto = new ParsedItemDto(title, url, img, upc, price);
        log.info(adInJsoupHtml.outerHtml());
        log.info(parsedItemDto.toString());
        return new ParsedItemDto(title, url, img, upc, price);
    }
}
