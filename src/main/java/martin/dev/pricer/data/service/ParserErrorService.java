package martin.dev.pricer.data.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.model.ParserError;
import martin.dev.pricer.data.repository.ParserErrorRepository;
import martin.dev.pricer.scraper.ParserException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Getter
public class ParserErrorService {

    private ParserErrorRepository parserErrorRepository;

    public ParserErrorService(ParserErrorRepository parserErrorRepository) {
        this.parserErrorRepository = parserErrorRepository;
    }

    public void saveError(ParserException e) {
//        ParserError parserError = new ParserError(e);
//        this.parserErrorRepository.save(parserError);
        System.out.println("Parser error occurred.");
    }

    public List<ParserError> fetchAll() {
        return this.parserErrorRepository.findAll();
    }
}
