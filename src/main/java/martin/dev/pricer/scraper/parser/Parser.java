package martin.dev.pricer.scraper.parser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public interface Parser {

    String makeNextPageUrl(String url, int pageNum);

    Elements parseListOfAdElements(Document pageContentInJsoupHtml);

    int parseMaxPageNum(Document pageContentInJsoupHtml);

    String parseTitle(Element adInJsoupHtml);

    String parseUpc(Element adInJsoupHtml);

    Double parsePrice(Element adInJsoupHtml);

    String parseImage(Element adInJsoupHtml);

    String parseUrl(Element adInJsoupHtml);

}
