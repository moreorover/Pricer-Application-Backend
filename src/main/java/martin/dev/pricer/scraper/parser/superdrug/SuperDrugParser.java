package martin.dev.pricer.scraper.parser.superdrug;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import martin.dev.pricer.scraper.parser.Parser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SuperDrugParser implements Parser {
    @Override
    public Elements parseListOfAdElements(Document pageContentInJsoupHtml) {
        return pageContentInJsoupHtml.select("div[class=item__content]");
    }

    @Override
    public int parseMaxPageNum(Document pageContentInJsoupHtml) {
        Elements paginationElements = pageContentInJsoupHtml.select("ul[class=pagination__list]");
        paginationElements = paginationElements.select("li");
        int countOfPaginationElements = paginationElements.size();
        if (countOfPaginationElements == 0) {
            return 0;
        } else {
            String elementText = paginationElements.get(countOfPaginationElements - 2).text();
            return Integer.parseInt(elementText);
        }
    }

    @Override
    public String parseTitle(Element adInJsoupHtml) {
        Element titleElement = adInJsoupHtml.selectFirst("a[class*=item__productName]");
        return titleElement.text();
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) {
        String urlString = parseUrl(adInJsoupHtml);
        String[] urlSplit = urlString.split("/p/");
        return "SD_" + urlSplit[1];
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) {
        String priceString = adInJsoupHtml.selectFirst("span[class*=item__price--now]").text();
        priceString = priceString.replaceAll("[^\\d.]", "");
        return Double.parseDouble(priceString);
    }

    @Override
    public String parseImage(Element adInJsoupHtml) {
        Element imgElement = adInJsoupHtml.selectFirst("img");
        String urlBase = "https://www.superdrug.com/";
        if (!imgElement.attr("src").equals("")) {
            return urlBase + imgElement.attr("src");
        } else {
            return urlBase + imgElement.attr("data-src");
        }
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) {
        Element titleElement = adInJsoupHtml.selectFirst("a[class*=item__productName]");
        String urlBase = "https://www.superdrug.com/";
        return urlBase + titleElement.attr("href");
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
