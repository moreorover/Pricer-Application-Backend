package martin.dev.pricer.data.fabric.product;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.fabric.EntityFactory;
import martin.dev.pricer.data.model.product.Category;
import martin.dev.pricer.data.model.product.Item;
import martin.dev.pricer.data.model.product.Price;
import martin.dev.pricer.data.model.store.StoreUrl;
import martin.dev.pricer.data.services.product.ItemService;
import martin.dev.pricer.data.services.product.PriceService;
import martin.dev.pricer.data.services.product.StatisticsService;
import martin.dev.pricer.scraper.model.ParsedItemDto;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Set;

@Slf4j
public class DealProcessor {

    private ItemService itemService;
    private PriceService priceService;
    private StatisticsService statisticsService;

    public DealProcessor(ItemService itemService, PriceService priceService, StatisticsService statisticsService) {
        this.itemService = itemService;
        this.priceService = priceService;
        this.statisticsService = statisticsService;
    }

    public void workOnData(ParsedItemDto parsedItemDto, StoreUrl storeUrl) {
//        log.info("Checking for: " + parsedItemDto.toString());
        Item itemInDb = itemService.findItemByUpc(parsedItemDto.getUpc());

        if (itemInDb != null) {
            if (itemInDb.getPrices().isEmpty() || itemInDb.getPrices() == null){
                itemService.delete(itemInDb);
                log.error("Deleted item!");
                return;
            }
//            log.info("Item found in db: " + itemInDb.toString());

            this.updateItemCategories(itemInDb, storeUrl.getCategories());

            if (getLastPrice(itemInDb) != parsedItemDto.getPrice()) {
                LocalDateTime localDateTime = LocalDateTime.now();
                double delta = calculateDelta(itemInDb, parsedItemDto);

                this.addNewPrice(itemInDb, parsedItemDto, localDateTime);

                this.updateStatistics(itemInDb, parsedItemDto.getPrice(), localDateTime, delta);

                itemService.save(itemInDb);

                statisticsService.save(itemInDb.getStatistics());
            }
        } else {
//            log.info("Not found, creating new Item");
            itemService.createNewItem(parsedItemDto, storeUrl);
        }
    }

    private void updateStatistics(Item item, double lastPrice, LocalDateTime localDateTime, double delta) {
        double minPrice = priceService.fetchMinPrice(item.getPrices());
        double maxPrice = priceService.fetchMaxPrice(item.getPrices());
        double avgPrice = priceService.fetchAvgPrice(item.getPrices());
        double avgDelta = priceService.fetchAvgDelta(item.getPrices());

        item.getStatistics().setLastPrice(lastPrice);
        item.getStatistics().setLastDelta(delta);
        item.getStatistics().setMinPrice(minPrice);
        item.getStatistics().setMaxPrice(maxPrice);
        item.getStatistics().setAvgPrice(avgPrice);
        item.getStatistics().setAvgDelta(avgDelta);
        item.getStatistics().setLastFound(localDateTime);

        if (lastPrice <= minPrice) {
            item.getStatistics().setDeal(true);
        } else {
            item.getStatistics().setDeal(false);
        }
    }

    private void updateItemCategories(Item item, Set<Category> categories) {
        if (!item.getCategories().equals(categories)) {
//            item.getCategories().clear();
            item.setCategories(categories);
        }
    }

    private double getLastPrice(Item item) {
        return priceService.fetchLastPriceForItem(item.getPrices());
    }

    private double calculateDelta(Item item, ParsedItemDto parsedItemDto) {
        double delta = 100 * ((parsedItemDto.getPrice() - getLastPrice(item)) / getLastPrice(item));
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(delta));
    }

    private void addNewPrice(Item item, ParsedItemDto parsedItemDto, LocalDateTime localDateTime) {
//        log.info("Adding new price to an Item");
        double delta = this.calculateDelta(item, parsedItemDto);
        Price newPrice = EntityFactory.createPrice(item, parsedItemDto, delta, localDateTime);
        log.info(newPrice.toString());
        item.getPrices().add(newPrice);
    }
}
