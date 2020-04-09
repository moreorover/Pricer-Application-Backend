package martin.dev.pricer.scraper;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParserValidator {

    public static void validateElement(Element element, Parser parser) throws ParserException {
        if (element == null) {
            throw new ParserException("Element object is null", parser);
        }
    }

    public static void validateElement(Element element, Parser parser, Element rootElement) throws ParserException {
        if (element == null) {
            throw new ParserException("Element object is null", parser, rootElement);
        }
    }

    public static void validateElements(Elements elements, Parser parser) throws ParserException {
        if (elements == null) {
            throw new ParserException("Elements object is null", parser);
        }
        if (elements.size() == 0) {
            throw new ParserException("Zero elements parsed", parser);
        }
    }

    public static void validateElementsNotNull(Elements elements, Parser parser) throws ParserException {
        if (elements == null) {
            throw new ParserException("Elements object is null", parser);
        }
    }

    public static void validatePositiveInteger(Integer number, Parser parser) throws ParserException {
        if (number == null) {
            throw new ParserException("Number type Integer is null", parser);
        }
        if (number <= 0) {
            throw new ParserException("Number type Integer is less or equal to 0", parser);
        }
    }

    public static void validatePositiveDouble(Double number, Parser parser) throws ParserException {
        if (number == null) {
            throw new ParserException("Number type Double is null", parser);
        }
        if (number <= 0.0) {
            throw new ParserException("Number type Double is less or equal to 0.0", parser);
        }
    }

    public static void validateStringIsNotEmpty(String string, Parser parser) throws ParserException {
        if (string == null) {
            throw new ParserException("Text is null", parser);
        }
        if (string.isBlank() || string.isEmpty()) {
            throw new ParserException("Text is empty", parser);
        }
    }

    public static void validateStringArray(String[] stringArray, int minimumSize, Parser parser) throws ParserException {
        if (stringArray == null) {
            throw new ParserException("String Array is null", parser);
        }
        if (stringArray.length < minimumSize ) {
            throw new ParserException("String array size required of: " + minimumSize + " found of " + stringArray.length, parser);
        }
    }

    public static void validateStringArray(String[] stringArray, int minimumSize, Parser parser, Element rootElement) throws ParserException {
        if (stringArray == null) {
            throw new ParserException("String Array is null", parser, rootElement);
        }
        if (stringArray.length < minimumSize ) {
            throw new ParserException("String array size required of: " + minimumSize + " found of " + stringArray.length, parser, rootElement);
        }
    }
}
