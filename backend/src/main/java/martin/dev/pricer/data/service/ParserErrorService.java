package martin.dev.pricer.data.service;

import martin.dev.pricer.data.model.ParserError;
import martin.dev.pricer.data.model.Url;
import martin.dev.pricer.data.repository.ParserErrorRepository;
import martin.dev.pricer.scraper.AbstractParser;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ParserErrorService {

    private final ParserErrorRepository parserErrorRepository;

    public ParserErrorService(ParserErrorRepository parserErrorRepository) {
        this.parserErrorRepository = parserErrorRepository;
    }

    public void logErrorToDatabase(int stepNumber, String parserOperation, AbstractParser parser) {
        ParserError parserError = new ParserError();
        parserError.setUrlObject(parser.getUrlObject());
        parserError.setUrl(parser.getCurrentPageUrl());
        parserError.setParserOperation(parserOperation);
        parserError.setStepNumber(stepNumber);
        parserError.setFoundTime(LocalDateTime.now());
        parserErrorRepository.save(parserError);
    }

}
