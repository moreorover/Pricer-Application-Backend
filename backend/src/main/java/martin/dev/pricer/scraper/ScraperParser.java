package martin.dev.pricer.scraper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public interface ScraperParser {
    Elements parseListOfAdElements(Document document);

    String parseAdTitle(Element adInJsoupHtml);

    String parseAdUpc(Element adInJsoupHtml);

    Double parseAdPrice(Element adInJsoupHtml);

    String parseAdImage(Element adInJsoupHtml);

    String parseAdUrl(Element adInJsoupHtml);

    boolean nextPageAvailable(Document document);
}
