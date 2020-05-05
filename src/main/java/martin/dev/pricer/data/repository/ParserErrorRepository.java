package martin.dev.pricer.data.repository;

import martin.dev.pricer.data.model.ParserError;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParserErrorRepository extends JpaRepository<ParserError, Long> {

}
