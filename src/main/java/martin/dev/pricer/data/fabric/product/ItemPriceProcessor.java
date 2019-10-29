package martin.dev.pricer.data.fabric.product;

import martin.dev.pricer.data.model.product.Item;
import martin.dev.pricer.data.model.product.Price;
import martin.dev.pricer.data.services.product.ItemRepository;
import martin.dev.pricer.data.services.product.PriceRepository;

import java.time.LocalDateTime;
import java.util.HashMap;

public class ItemPriceProcessor {

    private ItemRepository itemRepository;
    private PriceRepository priceRepository;

    public ItemPriceProcessor(ItemRepository itemRepository, PriceRepository priceRepository) {
        this.itemRepository = itemRepository;
        this.priceRepository = priceRepository;
    }

    public void checkAgainstDatabase(HashMap<String, String> dataSet){


        Item item = itemRepository.findItemByUpc(dataSet.get("upc"));
        if (item != null){
            Price lastPrice = priceRepository.findFirstByItemOrderByFoundAtDesc(item);
            if (lastPrice == null || lastPrice.getPrice() != Double.parseDouble(dataSet.get("price"))){
                Price price = new Price();
                price.setPrice(Double.parseDouble(dataSet.get("price")));
                price.setItem(item);
                price.setFoundAt(LocalDateTime.now());

                priceRepository.save(price);
            }
        } else {

            Item item1 = new Item();
            item1.setUrl(dataSet.get("url"));
            item1.setUpc(dataSet.get("upc"));
            item1.setTitle(dataSet.get("title"));
            item1.setImg(dataSet.get("img"));

            Price price = new Price();
            price.setPrice(Double.parseDouble(dataSet.get("price")));
            price.setItem(item1);
            price.setFoundAt(LocalDateTime.now());

            item1.getPrices().add(price);

            itemRepository.save(item1);

        }



    }
}
