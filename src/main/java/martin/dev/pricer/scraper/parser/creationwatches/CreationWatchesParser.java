package martin.dev.pricer.scraper.parser.creationwatches;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import martin.dev.pricer.scraper.parser.Parser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CreationWatchesParser implements Parser {

    @Override
    public Elements parseListOfAdElements(Document pageContentInJsoupHtml) {
        return pageContentInJsoupHtml.select("div[class=product-box]");
    }

    @Override
    public int parseMaxPageNum(Document pageContentInJsoupHtml) {
        Element countBox = pageContentInJsoupHtml.selectFirst("div[class=display-heading-box]").selectFirst("strong");
        String countString = countBox.text().split("of")[1];
        countString = countString.replaceAll("[^\\d.]", "");
        int adsCount = Integer.parseInt(countString);
        return (adsCount + 60 - 1) / 60;
    }

    @Override
    public String parseTitle(Element adInJsoupHtml) {
        Element titleElement = adInJsoupHtml.selectFirst("h3[class=product-name]").selectFirst("a");
        return titleElement.text();
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) {
        Element modelElement = adInJsoupHtml.selectFirst("p[class=product-model-no]");
        return "CW_" + modelElement.text().split(": ")[1];
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) {
        Element titleElement = adInJsoupHtml.selectFirst("p[class=product-price]").selectFirst("span");
        String priceString = titleElement.text().replaceAll("[^\\d.]", "");
        return Double.parseDouble(priceString);
    }

    @Override
    public String parseImage(Element adInJsoupHtml) {
        Element titleElement = adInJsoupHtml.selectFirst("div[class=product-img-box]").selectFirst("img");
        return titleElement.attr("src");
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) {
        Element titleElement = adInJsoupHtml.selectFirst("h3[class=product-name]").selectFirst("a");
        return titleElement.attr("href");
    }

    @Override
    public ParsedItemDto fetchItemDtoFromHtml(Element adInJsoupHtml) {
        log.info(adInJsoupHtml.outerHtml());
        String title = parseTitle(adInJsoupHtml);
        Double price = parsePrice(adInJsoupHtml);
        String img = parseImage(adInJsoupHtml);
        String upc = parseUpc(adInJsoupHtml);
        String url = parseUrl(adInJsoupHtml);

        ParsedItemDto parsedItemDto = new ParsedItemDto(title, url, img, upc, price);

        log.info(parsedItemDto.toString());
        return new ParsedItemDto(title, url, img, upc, price);
    }
}
