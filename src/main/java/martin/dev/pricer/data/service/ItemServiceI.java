package martin.dev.pricer.data.service;

import martin.dev.pricer.data.model.Item;
import martin.dev.pricer.data.model.Store;
import martin.dev.pricer.data.model.Url;
import martin.dev.pricer.scraper.model.ParsedItemDto;

public interface ItemServiceI {
    Item findByUpc(String upc);

    Item save(Item newItem);

    Item buildItemOfParsedData(ParsedItemDto parsedItemDto, Store store, Url url);

    double calculateDelta(double oldPrice, double newPrice);

    Item newPrice(Item item, double newPrice);

    void processParsedItem(ParsedItemDto parsedItemDto, Store store, Url url);
}
