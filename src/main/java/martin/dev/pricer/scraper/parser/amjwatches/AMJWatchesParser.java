package martin.dev.pricer.scraper.parser.amjwatches;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import martin.dev.pricer.scraper.parser.Parser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AMJWatchesParser implements Parser {

    @Override
    public Elements parseListOfAdElements(Document pageContentInJsoupHtml) {
        return pageContentInJsoupHtml.select("div[class=watch-sec]");
    }

    @Override
    public int parseMaxPageNum(Document pageContentInJsoupHtml) {
        Element productsCountElement = pageContentInJsoupHtml.selectFirst("div[class=items-on-page]");
        String countText = productsCountElement.children().last().text();
        countText = countText.replaceAll("[^\\d.]", "");
        int adsCount = Integer.parseInt(countText);
        return (adsCount + 40 - 1) / 40;
    }

    @Override
    public String parseTitle(Element adInJsoupHtml) {
        Element imgElement = adInJsoupHtml.selectFirst("div[class=watch-image]");
        imgElement = imgElement.selectFirst("a");
        return imgElement.attr("title");
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) {
        try {
            Element imgElement = adInJsoupHtml.selectFirst("div[class=watch-image]");
            imgElement = imgElement.selectFirst("a");
            String url = imgElement.attr("href");
            url = url.split(".uk/")[1];
            return "AMJW_" + url;
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) {
        Element detailsElement = adInJsoupHtml.selectFirst("div[class=watch-details]");

        String priceString = detailsElement.children().last().text().replaceAll("[^\\d.]", "");
        return Double.parseDouble(priceString);
    }

    @Override
    public String parseImage(Element adInJsoupHtml) {
        Element imgElement = adInJsoupHtml.selectFirst("div[class=watch-image]");
        imgElement = imgElement.selectFirst("a");
        imgElement = imgElement.selectFirst("img");
        return imgElement.attr("data-src");
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) {
        Element imgElement = adInJsoupHtml.selectFirst("div[class=watch-image]");
        imgElement = imgElement.selectFirst("a");
        return imgElement.attr("href");
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
