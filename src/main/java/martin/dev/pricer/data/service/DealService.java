package martin.dev.pricer.data.service;

import martin.dev.pricer.data.model.Deal;
import martin.dev.pricer.data.repository.DealRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DealService {

    private DealRepository dealRepository;

    public DealService(DealRepository dealRepository) {
        this.dealRepository = dealRepository;
    }

    public List<Deal> fetchDealsToPostToDiscord() {
        return this.dealRepository.findAllByPostedToDiscordFalseAndDealAvailableTrueOrderByFoundTimeAsc();
    }

    public void updateDealToPosted(Deal deal) {
        deal.setPostedToDiscord(true);
        this.dealRepository.save(deal);
    }

    public List<Deal> fetchAllDealsOderedByFounded(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return dealRepository.findAllByOrderByFoundTimeAsc(pageable);
    }
}
