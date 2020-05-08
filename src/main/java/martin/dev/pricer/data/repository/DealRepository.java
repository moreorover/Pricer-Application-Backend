package martin.dev.pricer.data.repository;

import martin.dev.pricer.data.model.Deal;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DealRepository extends JpaRepository<Deal, Long> {

    List<Deal> findAllByPostedToDiscordFalseAndDealAvailableTrueOrderByFoundTimeAsc();

    List<Deal> findAllByOrderByFoundTimeAsc(Pageable pageable);

}
