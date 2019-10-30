package martin.dev.pricer.data.services.product;

import martin.dev.pricer.data.model.dto.parse.ParsedItemDto;
import martin.dev.pricer.data.model.product.Category;
import martin.dev.pricer.data.model.product.Item;
import martin.dev.pricer.data.model.product.Price;
import martin.dev.pricer.data.model.store.StoreUrl;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ItemHandler {

    private ItemRepository itemRepository;

    public ItemHandler(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void createNewItemWithPrice(ParsedItemDto parsedItemDto, StoreUrl storeUrl){
        Item item = createItem(parsedItemDto, storeUrl);

        Price price = new Price();
        price.setPrice(parsedItemDto.getPrice());
        price.setItem(item);
        price.setDelta(0.0);
        price.setFoundAt(LocalDateTime.now());
        item.getPrices().add(price);

        itemRepository.save(item);
    }

    private Item createItem(ParsedItemDto parsedItemDto, StoreUrl storeUrl) {
        Item item = new Item();
        item.setUrl(parsedItemDto.getUrl());
        item.setUpc(parsedItemDto.getUpc());
        item.setTitle(parsedItemDto.getTitle());
        item.setImg(parsedItemDto.getImg());
        item.setStore(storeUrl.getStore());
        item.getCategories().addAll(storeUrl.getCategories());
        return item;
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
}
