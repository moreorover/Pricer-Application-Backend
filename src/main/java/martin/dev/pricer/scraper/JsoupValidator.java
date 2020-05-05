package martin.dev.pricer.scraper;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.service.ParserErrorService;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class JsoupValidator implements ParserValidator {

    private final ParserErrorService parserErrorService;

    public JsoupValidator(ParserErrorService parserErrorService) {
        this.parserErrorService = parserErrorService;
    }

    @Override
    public void validate(Element element, int stepNumber, String parserOperation, AbstractParser parser) throws ParserException {
        if (element == null) {
            log.warn("Null at parser operation " + parserOperation + " step number " + stepNumber);
            parserErrorService.logErrorToDatabase(stepNumber, parserOperation, parser);
            throw new ParserException("Element null");
        }
        if (element.outerHtml().isEmpty() || element.outerHtml().isBlank()) {
            log.warn("Empty at parser operation " + parserOperation + " step number " + stepNumber);
            parserErrorService.logErrorToDatabase(stepNumber, parserOperation, parser);
            throw new ParserException("Element is empty");
        }
    }

    @Override
    public void validate(Elements elements, int stepNumber, String parserOperation, AbstractParser parser) throws ParserException {
        if (elements == null) {
            log.warn("Null at parser operation " + parserOperation + " step number " + stepNumber);
            parserErrorService.logErrorToDatabase(stepNumber, parserOperation, parser);
            throw new ParserException("Element null");
        }
        if (elements.outerHtml().isEmpty() || elements.outerHtml().isBlank()) {
            log.warn("Empty at parser operation " + parserOperation + " step number " + stepNumber);
            parserErrorService.logErrorToDatabase(stepNumber, parserOperation, parser);
            throw new ParserException("Element is empty");
        }
    }

    public void validate(String string, int stepNumber, String parserOperation, AbstractParser parser) throws ParserException {
        if (string == null) {
            log.warn("Null at parser operation " + parserOperation + " step number " + stepNumber);
            parserErrorService.logErrorToDatabase(stepNumber, parserOperation, parser);
            throw new ParserException("Element null");
        }
        if (string.isEmpty() || string.isBlank()) {
            log.warn("Empty at parser operation " + parserOperation + " step number " + stepNumber);
            parserErrorService.logErrorToDatabase(stepNumber, parserOperation, parser);
            throw new ParserException("String is empty");
        }
    }

    @Override
    public void validate(Integer number, int stepNumber, String parserOperation, AbstractParser parser) throws ParserException {
        if (number == null) {
            log.warn("Null at parser operation " + parserOperation + " step number " + stepNumber);
            parserErrorService.logErrorToDatabase(stepNumber, parserOperation, parser);
            throw new ParserException("Number is null");
        }
        if (number <= 0) {
            log.warn("Empty at parser operation " + parserOperation + " step number " + stepNumber);
            parserErrorService.logErrorToDatabase(stepNumber, parserOperation, parser);
            throw new ParserException("Number is 0 or less");
        }
    }

    @Override
    public void validate(Double number, int stepNumber, String parserOperation, AbstractParser parser) throws ParserException {
        if (number == null) {
            log.warn("Null at parser operation " + parserOperation + " step number " + stepNumber);
            parserErrorService.logErrorToDatabase(stepNumber, parserOperation, parser);
            throw new ParserException("Number is null");
        }
        if (number <= 0) {
            log.warn("Empty at parser operation " + parserOperation + " step number " + stepNumber);
            parserErrorService.logErrorToDatabase(stepNumber, parserOperation, parser);
            throw new ParserException("Number is 0 or less");
        }
    }

    @Override
    public void validate(String[] stringArray, int stepNumber, String parserOperation, AbstractParser parser) throws ParserException {
        if (stringArray == null) {
            log.warn("Null at parser operation " + parserOperation + " step number " + stepNumber);
            parserErrorService.logErrorToDatabase(stepNumber, parserOperation, parser);
            throw new ParserException("String Array is null");
        }
        if (stringArray.length < 1) {
            log.warn("Empty at parser operation " + parserOperation + " step number " + stepNumber);
            parserErrorService.logErrorToDatabase(stepNumber, parserOperation, parser);
            throw new ParserException("String array length is less than zero");
        }
    }
}
