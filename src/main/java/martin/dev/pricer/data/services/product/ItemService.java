package martin.dev.pricer.data.services.product;

import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service
public class ItemService {

    @Autowired
    private StatisticsService statisticsService;
    @Autowired
    private PriceService priceService;

    private ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void createNewItem(ParsedItemDto parsedItemDto, StoreUrl storeUrl){
        LocalDateTime localDateTime = LocalDateTime.now();

        Item item = createItem(parsedItemDto, storeUrl);
        Item savedItem = save(item);
        Price price = EntityFactory.createPrice(savedItem, parsedItemDto, 0.0, localDateTime);
        priceService.save(price);

//        log.info("Created Item: " + savedItem.toString());

        Statistics statistics = EntityFactory.createStatistics(item, parsedItemDto, localDateTime);

        statisticsService.save(statistics);
    }

    public void delete(Item item) {
        itemRepository.delete(item);
    }

    public Item findItemByUpc(ParsedItemDto parsedItemDto){
        return itemRepository.findItemByUpc(parsedItemDto.getUpc());
    }

    public Item findItemByUpc(String parsedItemDtoUpc){
        return itemRepository.findItemByUpc(parsedItemDtoUpc);
    }

    public void updateItemCategories(Item item, Set<Category> categories) {
        if (!item.getCategories().equals(categories)) {
            item.getCategories().clear();
            item.setCategories(categories);
        }
    }

    public List<Item> fetchAll(){
        return itemRepository.findAll();
    }

    public void saveAll(List<Item> items) {
        itemRepository.saveAll(items);
    }

    public Item save(Item item){
        return itemRepository.save(item);
    }

    public Item fetchItemStatNull(){
        return itemRepository.findFirstByStatisticsNull();
    }

    private Item createItem(ParsedItemDto parsedItemDto, StoreUrl storeUrl){
        Item item = new Item();
        item.setUrl(parsedItemDto.getUrl());
        item.setUpc(parsedItemDto.getUpc());
        item.setTitle(parsedItemDto.getTitle());
        item.setImg(parsedItemDto.getImg());
        item.setStore(storeUrl.getStore());
        item.getCategories().addAll(storeUrl.getCategories());
        return item;
    }

}
