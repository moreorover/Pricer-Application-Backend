package martin.dev.pricer.data.fabric.product;

import martin.dev.pricer.data.model.dto.parse.ParsedItemDto;
import martin.dev.pricer.data.model.product.Item;
import martin.dev.pricer.data.model.product.Price;
import martin.dev.pricer.data.model.store.Store;
import martin.dev.pricer.data.services.product.ItemRepository;
import martin.dev.pricer.data.services.product.PriceRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class ItemPriceProcessor {

    private ItemRepository itemRepository;
    private PriceRepository priceRepository;

    public ItemPriceProcessor(ItemRepository itemRepository, PriceRepository priceRepository) {
        this.itemRepository = itemRepository;
        this.priceRepository = priceRepository;
    }

    public void checkAgainstDatabase(List<ParsedItemDto> dataSet, Store store){

        dataSet.forEach(parsedItemDto -> {
            Item item = itemRepository.findItemByUpc(parsedItemDto.getUpc());
            if (item != null){
                Price lastPrice = priceRepository.findFirstByItemOrderByFoundAtDesc(item);
                if (lastPrice == null || lastPrice.getPrice() != parsedItemDto.getPrice()){
                    Price price = new Price();
                    price.setPrice(parsedItemDto.getPrice());
                    price.setItem(item);
                    price.setFoundAt(LocalDateTime.now());

                    priceRepository.save(price);
                }
            } else {

                Item item1 = new Item();
                item1.setUrl(parsedItemDto.getUrl());
                item1.setUpc(parsedItemDto.getUpc());
                item1.setTitle(parsedItemDto.getTitle());
                item1.setImg(parsedItemDto.getImg());
                item1.setStore(store);

                Price price = new Price();
                price.setPrice(parsedItemDto.getPrice());
                price.setItem(item1);
                price.setFoundAt(LocalDateTime.now());

                item1.getPrices().add(price);

                itemRepository.save(item1);

            }
        });
    }
}
