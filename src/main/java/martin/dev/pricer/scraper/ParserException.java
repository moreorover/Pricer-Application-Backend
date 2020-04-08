package martin.dev.pricer.scraper;

public class ParserException extends Exception {

    private Parser parser;

    public ParserException(String message) {
        super(message);
    }

    public ParserException(String message, Parser parser) {
        super(message);
        this.parser = parser;
    }

    public Parser getParser() {
        return parser;
    }
}
