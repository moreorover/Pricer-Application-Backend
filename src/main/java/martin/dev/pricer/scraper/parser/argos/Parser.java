package martin.dev.pricer.scraper.parser.argos;

import martin.dev.pricer.scraper.model.ParsedItemDto;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public interface Parser {

    Elements parseListOfAdElements(Document pageContentInJsoupHtml);

    int parseMaxPageNum(Document pageContentInJsoupHtml);

    String parseTitle(Element adInJsoupHtml);

    String parseUpc(Element adInJsoupHtml);

    Double parsePrice(Element adInJsoupHtml);

    String parseImage(Element adInJsoupHtml);

    String parseUrl(Element adInJsoupHtml);

    ParsedItemDto fetchObject(Element adInJsoupHtml);
}
