package martin.dev.pricer.data.fabric.product;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.model.product.Item;
import martin.dev.pricer.data.model.product.Price;
import martin.dev.pricer.data.model.store.StoreUrl;
import martin.dev.pricer.data.services.product.ItemService;
import martin.dev.pricer.data.services.product.PriceService;
import martin.dev.pricer.data.services.product.StatisticsService;
import martin.dev.pricer.scraper.model.ParsedItemDto;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
public class ItemPriceProcessor {

    private ItemService itemService;
    private PriceService priceService;
    private StatisticsService statisticsService;

    public ItemPriceProcessor(ItemService itemService, PriceService priceService, StatisticsService statisticsService) {
        this.itemService = itemService;
        this.priceService = priceService;
        this.statisticsService = statisticsService;
    }
}
