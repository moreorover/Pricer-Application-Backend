package martin.dev.pricer.data.services.product;

import martin.dev.pricer.data.fabric.EntityFactory;
import martin.dev.pricer.data.model.product.Statistics;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import martin.dev.pricer.data.model.product.Category;
import martin.dev.pricer.data.model.product.Item;
import martin.dev.pricer.data.model.product.Price;
import martin.dev.pricer.data.model.store.StoreUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class ItemService {

    @Autowired
    private StatisticsService statisticsService;

    private ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void createNewItem(ParsedItemDto parsedItemDto, StoreUrl storeUrl){
        LocalDateTime localDateTime = LocalDateTime.now();

        Item item = EntityFactory.createItem(parsedItemDto, storeUrl);

        Price price = EntityFactory.createPrice(item, parsedItemDto, 0.0, localDateTime);
        save(item);

        Statistics statistics = EntityFactory.createStatistics(item, parsedItemDto, localDateTime);

        statisticsService.save(statistics);
    }

    public Item findItemByUpc(ParsedItemDto parsedItemDto){
        return itemRepository.findItemByUpc(parsedItemDto.getUpc());
    }

    public void updateItemCategories(Item item, Set<Category> categories) {
        if (!item.getCategories().equals(categories)) {
            item.getCategories().clear();
            item.getCategories().addAll(categories);
            itemRepository.save(item);
        }
    }

    public List<Item> fetchAll(){
        return itemRepository.findAll();
    }

    public void saveAll(List<Item> items) {
        itemRepository.saveAll(items);
    }

    public void save(Item item){
        itemRepository.save(item);
    }

    public Item fetchItemStatNull(){
        return itemRepository.findFirstByStatisticsNull();
    }
}
