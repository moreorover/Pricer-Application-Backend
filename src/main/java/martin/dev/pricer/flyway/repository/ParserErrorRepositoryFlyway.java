package martin.dev.pricer.flyway.repository;

import martin.dev.pricer.flyway.model.ParserError;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParserErrorRepositoryFlyway extends JpaRepository<ParserError, Long> {

}
