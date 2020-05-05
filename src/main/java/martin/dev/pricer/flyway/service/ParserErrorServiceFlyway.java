package martin.dev.pricer.flyway.service;

import martin.dev.pricer.flyway.model.ParserError;
import martin.dev.pricer.flyway.repository.ParserErrorRepositoryFlyway;
import org.springframework.stereotype.Service;

@Service
public class ParserErrorServiceFlyway {

    private final ParserErrorRepositoryFlyway parserErrorRepository;

    public ParserErrorServiceFlyway(ParserErrorRepositoryFlyway parserErrorRepository) {
        this.parserErrorRepository = parserErrorRepository;
    }

    public void logErrorToDatabase(int stepNumber, String parserOperation) {
        ParserError parserError = new ParserError();

        parserErrorRepository.save(parserError);
    }

}
