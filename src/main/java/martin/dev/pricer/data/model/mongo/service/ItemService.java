package martin.dev.pricer.data.model.mongo.service;

import martin.dev.pricer.data.model.mongo.model.Item;
import martin.dev.pricer.data.model.mongo.model.Price;
import martin.dev.pricer.data.model.mongo.model.Store;
import martin.dev.pricer.data.model.mongo.model.Url;
import martin.dev.pricer.scraper.model.ParsedItemDto;

public interface ItemService {
    Item findByUpc(String upc);

    Item save(Item newItem);

    Item buildItemOfParsedData(ParsedItemDto parsedItemDto, Store store, Url url);

    double calculateDelta(double oldPrice, double newPrice);

    Item newPrice(Item item, double newPrice);

    void processParsedItem(ParsedItemDto parsedItemDto, Store store, Url url);
}
