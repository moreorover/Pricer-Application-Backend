package martin.dev.pricer.data.services.product;

import martin.dev.pricer.data.model.product.Item;
import martin.dev.pricer.data.model.product.Statistics;

import java.time.LocalDateTime;

public class StatisticsService {

    private StatisticsRepository statisticsRepository;

    public StatisticsService(StatisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
    }

    public void save(Statistics statistics) {
        statisticsRepository.save(statistics);
    }

    public Statistics findByItem(Item item) {
        return statisticsRepository.findFirstByItem(item);
    }

    public void updateStatistics(Item item, double lastPrice, double lastDelta, double minPrice, double maxPrice, double avgPrice, double avgDelta, LocalDateTime localDateTime) {
        Statistics statistics = findByItem(item);

        statistics.setLastPrice(lastPrice);
        statistics.setLastDelta(lastDelta);
        statistics.setMinPrice(minPrice);
        statistics.setMaxPrice(maxPrice);
        statistics.setAvgPrice(avgPrice);
        statistics.setAvgDelta(avgDelta);
        statistics.setLastFound(localDateTime);

        if (lastPrice <= minPrice){
            statistics.setDeal(true);
        } else {
            statistics.setDeal(false);
        }

        statisticsRepository.save(statistics);
    }
}
