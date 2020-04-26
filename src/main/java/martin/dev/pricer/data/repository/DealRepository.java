package martin.dev.pricer.data.repository;

import martin.dev.pricer.data.model.Deal;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DealRepository extends PagingAndSortingRepository<Deal, String> {

    List<Deal> findByOrderByDealFoundDesc();

    List<Deal> findByOrderByDealFoundDesc(Pageable pageable);

    List<Deal> findByAvailableOrderByDealFoundDesc(boolean available ,Pageable pageable);

    List<Deal> findByAvailableAndStore_idOrderByDealFoundDesc(boolean available, String store_id ,Pageable pageable);

    Deal findFirstByItem_IdAndAvailable(String item_id, boolean available);
}
