package martin.dev.pricer.data.repository;

import martin.dev.pricer.data.model.ParserError;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParserErrorRepository extends PagingAndSortingRepository<ParserError, String> {

    List<ParserError> findAll();
}
