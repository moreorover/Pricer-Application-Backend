package martin.dev.pricer.scraper.parser.unactive.allbeauty;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import martin.dev.pricer.scraper.parser.Parser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AllBeautyParser implements Parser {

    @Override
    public Elements parseListOfAdElements(Document pageContentInJsoupHtml) {
        return pageContentInJsoupHtml.select("div[class^=list-item]");
    }

    @Override
    public int parseMaxPageNum(Document pageContentInJsoupHtml) {
        Element productsCountElement = pageContentInJsoupHtml.selectFirst("span[id=resultsTotal]");
        int adsCount = Integer.parseInt(productsCountElement.text());
        return (adsCount + 36 - 1) / 36;
    }

    @Override
    public String parseTitle(Element adInJsoupHtml) {
        Element detailsElement = adInJsoupHtml.selectFirst("div[class^=list-item-details]");
        Element titleElement = detailsElement.selectFirst("p");
        return titleElement.text().trim();
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) {
        Element upcElement = adInJsoupHtml.selectFirst("input[name=UC_recordId]");
        String upc = upcElement.attr("value");
        return "AB_" + upc;
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) {
        Element priceElement = adInJsoupHtml.selectFirst("p[class=list-item-price]");
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
        Element urlElement = adInJsoupHtml.selectFirst("div[class=list-item-buttons]");
        urlElement = urlElement.selectFirst("a");
        return urlElement.attr("href");
    }

    @Override
    public ParsedItemDto fetchItemDtoFromHtml(Element adInJsoupHtml) {
        return null;
    }
}
