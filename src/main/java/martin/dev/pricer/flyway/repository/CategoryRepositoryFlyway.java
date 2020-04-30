package martin.dev.pricer.flyway.repository;

import martin.dev.pricer.flyway.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepositoryFlyway extends JpaRepository<Category, Long> {
}
