package martin.dev.pricer.data.fabric.product;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.model.product.Item;
import martin.dev.pricer.data.model.product.Price;
import martin.dev.pricer.data.model.product.Statistics;
import martin.dev.pricer.data.services.product.ItemService;
import martin.dev.pricer.data.services.product.PriceService;
import martin.dev.pricer.data.services.product.StatisticsService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class StatisticsProcessor {

    private ItemService itemService;
    private PriceService priceService;
    private StatisticsService statisticsService;

    public StatisticsProcessor(ItemService itemService, PriceService priceService, StatisticsService statisticsService) {
        this.itemService = itemService;
        this.priceService = priceService;
        this.statisticsService = statisticsService;
    }

//    @Scheduled(fixedRate = 990000000, initialDelay = 5000)
    public void buildData() {
        log.info("Starting to build stats");
        Item item = itemService.fetchItemStatNull();
        while (item != null) {
            log.info(item.toString());
            Statistics stats = new Statistics();

            DecimalFormat df = new DecimalFormat("#.##");

            double lastPrice = priceService.fetchLastPriceForItem(item).getPrice();
            stats.setLastPrice(lastPrice);
            double lastDelta = Double.parseDouble(df.format(priceService.fetchLastPriceForItem(item).getDelta()));
            stats.setLastDelta(lastDelta);
            stats.setLastFound(priceService.fetchLastPriceForItem(item).getFoundAt());

            List<Price> prices = priceService.fetchPrices(item);
            double avgPrice = priceService.fetchAvgPrice((Set<Price>) prices);
            stats.setAvgPrice(avgPrice);

            double avgDelta = priceService.fetchAvgDelta((Set<Price>) prices);
            stats.setAvgDelta(avgDelta);

            double maxPrice = priceService.fetchMaxPrice(item).getPrice();
            double minPrice = priceService.fetchMinPrice(item).getPrice();

            stats.setMaxPrice(maxPrice);
            stats.setMinPrice(minPrice);

            if (lastPrice <= minPrice && lastDelta < 0 && lastDelta < -9){
                stats.setDeal(true);
            } else {
                stats.setDeal(false);
            }


            stats.setItem(item);
            statisticsService.save(stats);

            item = itemService.fetchItemStatNull();
        }
    }

}
