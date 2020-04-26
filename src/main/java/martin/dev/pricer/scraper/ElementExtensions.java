package martin.dev.pricer.scraper;

import org.jsoup.nodes.Element;

public class ElementExtensions {

    public static void validateElement(Element element) throws ParserException {
        if (element == null) {
            throw new ParserException("Element object is null");
        }
    }
}
