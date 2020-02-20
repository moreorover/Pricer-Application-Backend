package martin.dev.pricer.data.model.mongo.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.model.mongo.model.Item;
import martin.dev.pricer.data.model.mongo.repository.MongoItemRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Getter
public class MongoItemService implements ItemService {

    private MongoItemRepository itemRepository;

    public MongoItemService(MongoItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Item findByUpc(String upc) {
        return this.itemRepository.findByUpc(upc);
    }

    @Override
    public Item save(Item newItem) {
        return this.itemRepository.save(newItem);
    }
}
