package martin.dev.pricer.scraper.parser.argos;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ArgosParser implements Parser {

    @Override
    public Elements parseListOfAdElements(Document pageContentInJsoupHtml) {
        return pageContentInJsoupHtml.select("div[class^=ProductCardstyles__Wrapper-]");
    }

    @Override
    public int parseMaxPageNum(Document pageContentInJsoupHtml) {
        Element searchResultsCount = pageContentInJsoupHtml.selectFirst("div[class*=search-results-count]");
        String countString = searchResultsCount.attr("data-search-results");
        int count = Integer.parseInt(countString);
//        int maxPages = (int) Math.ceil(count / 30.0);
        return (count + 30 - 1) / 30;
    }

    @Override
    public String parseTitle(Element adInJsoupHtml){
        Element titleElement = adInJsoupHtml.selectFirst("a[class*=Title]");
        return titleElement.text();
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) {
        return "A_" + adInJsoupHtml.attr("data-product-id");
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) {
        String priceString = adInJsoupHtml.selectFirst("div[class*=PriceText]").text();
        priceString = priceString.replaceAll("[^\\d.]", "");
        return Double.parseDouble(priceString);
    }

    @Override
    public String parseImage(Element adInJsoupHtml) {
        Element imgElement = adInJsoupHtml.selectFirst("div[class*=ImageWrapper]");
        imgElement = imgElement.selectFirst("picture");
        imgElement = imgElement.selectFirst("img");
        return imgElement.attr("src");
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) {
        Element urlElement = adInJsoupHtml.selectFirst("a");
        String urlBase = "https://www.argos.co.uk";
        return urlBase + urlElement.attr("href");
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
