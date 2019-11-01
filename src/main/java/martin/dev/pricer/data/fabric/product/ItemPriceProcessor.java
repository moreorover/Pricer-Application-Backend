package martin.dev.pricer.data.fabric.product;

import martin.dev.pricer.scraper.model.ParsedItemDto;
import martin.dev.pricer.data.model.product.Item;
import martin.dev.pricer.data.model.product.Price;
import martin.dev.pricer.data.model.store.StoreUrl;
import martin.dev.pricer.data.services.product.ItemService;
import martin.dev.pricer.data.services.product.PriceService;

import java.text.DecimalFormat;
import java.util.List;

public class ItemPriceProcessor {

    private ItemService itemService;
    private PriceService priceService;

    public ItemPriceProcessor(ItemService itemService, PriceService priceService) {
        this.itemService = itemService;
        this.priceService = priceService;
    }

    public void checkAgainstDatabase(List<ParsedItemDto> dataSet, StoreUrl storeUrl) {

        dataSet.forEach(parsedItemDto -> {
            Item item = itemService.findItemByUpc(parsedItemDto);
            if (item != null) {
                itemService.updateItemCategories(item, storeUrl.getCategories());
                Price lastPrice = priceService.fetchLastPriceForItem(item);
                if (lastPrice == null) {
                    priceService.firstPriceForItem(parsedItemDto, item);
                } else if (lastPrice.getPrice() != parsedItemDto.getPrice()) {
                    double delta = 100 * ((parsedItemDto.getPrice() - lastPrice.getPrice()) / lastPrice.getPrice());
                    DecimalFormat df = new DecimalFormat("#.##");
                    delta = Double.parseDouble(df.format(delta));
                    priceService.changedPriceForItem(parsedItemDto, item, delta);
                }
            } else {
                itemService.createNewItemWithPrice(parsedItemDto, storeUrl);
            }
        });
    }
}
