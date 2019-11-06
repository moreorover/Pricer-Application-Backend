package martin.dev.pricer.data.fabric.product;

import martin.dev.pricer.data.services.product.StatisticsService;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import martin.dev.pricer.data.model.product.Item;
import martin.dev.pricer.data.model.product.Price;
import martin.dev.pricer.data.model.store.StoreUrl;
import martin.dev.pricer.data.services.product.ItemService;
import martin.dev.pricer.data.services.product.PriceService;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;

public class ItemPriceProcessor {

    private ItemService itemService;
    private PriceService priceService;
    private StatisticsService statisticsService;

    public ItemPriceProcessor(ItemService itemService, PriceService priceService, StatisticsService statisticsService) {
        this.itemService = itemService;
        this.priceService = priceService;
        this.statisticsService = statisticsService;
    }

    public void checkAgainstDatabase(List<ParsedItemDto> dataSet, StoreUrl storeUrl) {

        dataSet.forEach(parsedItemDto -> {
            Item item = itemService.findItemByUpc(parsedItemDto);
            if (item != null) {
                itemService.updateItemCategories(item, storeUrl.getCategories());
                double lastPrice = item.getStatistics().getLastPrice();
                if (lastPrice != parsedItemDto.getPrice()) {
                    LocalDateTime localDateTime = LocalDateTime.now();
                    double delta = 100 * ((parsedItemDto.getPrice() - lastPrice) / lastPrice);
                    DecimalFormat df = new DecimalFormat("#.##");
                    delta = Double.parseDouble(df.format(delta));
                    priceService.changedPriceForItem(parsedItemDto, item, delta, localDateTime);

                    List<Price> pricesOfItem = priceService.fetchPrices(item);

                    double minPrice = priceService.fetchMinPrice(pricesOfItem);
                    double maxPrice = priceService.fetchMaxPrice(pricesOfItem);
                    double avgPrice = priceService.fetchAvgPrice(pricesOfItem);
                    double avgDelta = priceService.fetchAvgDelta(pricesOfItem);

                    statisticsService.updateStatistics(item, parsedItemDto.getPrice(), delta, minPrice, maxPrice, avgPrice, avgDelta, localDateTime);
                }
            } else {
                itemService.createNewItem(parsedItemDto, storeUrl);
            }
        });
    }
}
