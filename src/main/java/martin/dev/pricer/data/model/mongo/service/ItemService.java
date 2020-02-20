package martin.dev.pricer.data.model.mongo.service;

import martin.dev.pricer.data.model.mongo.model.Item;

public interface ItemService {
    Item findByUpc(String upc);

    Item save(Item newItem);
}
