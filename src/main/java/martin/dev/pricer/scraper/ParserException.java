package martin.dev.pricer.scraper;

import org.jsoup.nodes.Element;

public class ParserException extends Exception {

    private AbstractParser parser;
    private Element element;

    public ParserException(String message) {
        super(message);
    }

    public ParserException(String message, AbstractParser parser) {
        super(message);
        this.parser = parser;
    }

    public ParserException(String message, AbstractParser parser, Element element) {
        super(message);
        this.parser = parser;
        this.element = element;
    }

    public AbstractParser getParser() {
        return parser;
    }

    public Element getElement() {
        return element;
    }
}
