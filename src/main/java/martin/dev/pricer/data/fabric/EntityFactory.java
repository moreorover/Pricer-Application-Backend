package martin.dev.pricer.data.fabric;

import martin.dev.pricer.data.model.product.Item;
import martin.dev.pricer.data.model.product.Price;
import martin.dev.pricer.data.model.product.Statistics;
import martin.dev.pricer.data.model.store.StoreUrl;
import martin.dev.pricer.scraper.model.ParsedItemDto;

import java.time.LocalDateTime;

public class EntityFactory {

    public static Item createItem(ParsedItemDto parsedItemDto, StoreUrl storeUrl){
        Item item = new Item();
        item.setUrl(parsedItemDto.getUrl());
        item.setUpc(parsedItemDto.getUpc());
        item.setTitle(parsedItemDto.getTitle());
        item.setImg(parsedItemDto.getImg());
        item.setStore(storeUrl.getStore());
        item.getCategories().addAll(storeUrl.getCategories());
        return item;
    }

    public static Price createPrice(Item item, ParsedItemDto parsedItemDto, double delta, LocalDateTime localDateTime){
        Price price = new Price();
        price.setPrice(parsedItemDto.getPrice());
        price.setItem(item);
        price.setDelta(delta);
        price.setFoundAt(localDateTime);
        return price;
    }

    public static Statistics createStatistics(Item item, ParsedItemDto parsedItemDto, LocalDateTime localDateTime){
        Statistics statistics = new Statistics();
        statistics.setLastPrice(parsedItemDto.getPrice());
        statistics.setMinPrice(parsedItemDto.getPrice());
        statistics.setMaxPrice(parsedItemDto.getPrice());
        statistics.setAvgPrice(parsedItemDto.getPrice());
        statistics.setLastDelta(0.0);
        statistics.setAvgDelta(0.0);
        statistics.setLastFound(localDateTime);
        statistics.setDeal(false);
        statistics.setItem(item);
        return statistics;
    }
}
