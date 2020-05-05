package martin.dev.pricer.scraper;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public interface ParserValidator {
    void validate(Element element, int stepNumber, String parserOperation) throws ParserException;

    void validate(Elements element, int stepNumber, String parserOperation) throws ParserException;

    void validate(String string, int stepNumber, String parserOperation) throws ParserException;

    void validate(Integer number, int stepNumber, String parserOperation) throws ParserException;

    void validate(Double number, int stepNumber, String parserOperation) throws ParserException;

    void validate(String[] stringArray, int stepNumber, String parserOperation) throws ParserException;
}
