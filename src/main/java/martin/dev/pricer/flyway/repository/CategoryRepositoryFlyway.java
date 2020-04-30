package martin.dev.pricer.flyway.repository;

import martin.dev.pricer.flyway.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepositoryFlyway extends JpaRepository<Category, Long> {
}
