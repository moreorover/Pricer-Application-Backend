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

        item.getStatistics().setLastPrice(lastPrice);
        item.getStatistics().setLastDelta(lastDelta);
        item.getStatistics().setMinPrice(minPrice);
        item.getStatistics().setMaxPrice(maxPrice);
        item.getStatistics().setAvgPrice(avgPrice);
        item.getStatistics().setAvgDelta(avgDelta);
        item.getStatistics().setLastFound(localDateTime);

        if (lastPrice <= minPrice){
            item.getStatistics().setDeal(true);
        } else {
            item.getStatistics().setDeal(false);
        }
    }
}
