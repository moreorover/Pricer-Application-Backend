package martin.dev.pricer.scraper.parser.hsamuel;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import martin.dev.pricer.scraper.parser.Parser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HSamuelParser implements Parser {

    @Override
    public Elements parseListOfAdElements(Document pageContentInJsoupHtml) {
        return pageContentInJsoupHtml.select("div.product-tile.js-product-item");
    }

    @Override
    public int parseMaxPageNum(Document pageContentInJsoupHtml) {
        Element paginationBlockElement = pageContentInJsoupHtml.selectFirst("ol[class*=pageNumbers]");
        Elements paginationButtons = paginationBlockElement.select("li");
        Element lastPageElement = paginationButtons.get(5);
        String lastPageText = lastPageElement.text();
        return Integer.parseInt(lastPageText);
    }

    @Override
    public String parseTitle(Element adInJsoupHtml) {
        return adInJsoupHtml.select("p[class=product-tile__description]").text();
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) {
        Element upcElement = adInJsoupHtml.selectFirst("meta");
        return "HS_" + upcElement.attr("content");
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) {
        String priceString = adInJsoupHtml.select("p[class*=current-price]").text();
        priceString = priceString.replaceAll("[^\\d.]", "");
        return Double.parseDouble(priceString);
    }

    @Override
    public String parseImage(Element adInJsoupHtml) {
        Element imgElement = adInJsoupHtml.selectFirst("img[class^=product-tile__image]");
        return imgElement.attr("data-src");
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) {
        Element element = adInJsoupHtml.select("a").first();
        String urlBase = "https://www.hsamuel.co.uk";
        return urlBase + element.attr("href");
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
