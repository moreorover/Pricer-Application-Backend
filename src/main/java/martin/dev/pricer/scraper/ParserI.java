package martin.dev.pricer.scraper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public interface ParserI {

    String makeNextPageUrl(String url, int pageNum);

    Elements parseListOfAdElements(Document pageContentInJsoupHtml) throws ParserException;

    int parseMaxPageNum(Document pageContentInJsoupHtml) throws ParserException;

    String parseTitle(Element adInJsoupHtml) throws ParserException;

    String parseUpc(Element adInJsoupHtml) throws ParserException;

    Double parsePrice(Element adInJsoupHtml) throws ParserException;

    String parseImage(Element adInJsoupHtml) throws ParserException;

    String parseUrl(Element adInJsoupHtml) throws ParserException;
}
