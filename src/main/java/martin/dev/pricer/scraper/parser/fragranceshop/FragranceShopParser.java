package martin.dev.pricer.scraper.parser.fragranceshop;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import martin.dev.pricer.scraper.parser.Parser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FragranceShopParser implements Parser {

    @Override
    public Elements parseListOfAdElements(Document pageContentInJsoupHtml) {
        return pageContentInJsoupHtml.select("div[class=product-container-panel]");
    }

    @Override
    public int parseMaxPageNum(Document pageContentInJsoupHtml) {
        Element productCountElement = pageContentInJsoupHtml.selectFirst("div[class=product-count]");
        String textOfElement = productCountElement.text();
        String productCountString = textOfElement.split("of")[1];
        productCountString = productCountString.replaceAll("[^\\d.]", "");
        int productCount = Integer.parseInt(productCountString);
        return (productCount + 20 - 1) / 30;
    }

    @Override
    public String parseTitle(Element adInJsoupHtml) {
        return null;
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) {
        return null;
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) {
        return null;
    }

    @Override
    public String parseImage(Element adInJsoupHtml) {
        return null;
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) {
        return null;
    }

    @Override
    public ParsedItemDto fetchItemDtoFromHtml(Element adInJsoupHtml) {
        return null;
    }
}
