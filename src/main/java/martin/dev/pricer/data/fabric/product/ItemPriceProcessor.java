package martin.dev.pricer.data.fabric.product;

import martin.dev.pricer.data.model.dto.parse.ParsedItemDto;
import martin.dev.pricer.data.model.product.Item;
import martin.dev.pricer.data.model.product.Price;
import martin.dev.pricer.data.model.store.StoreUrl;
import martin.dev.pricer.data.services.product.ItemHandler;
import martin.dev.pricer.data.services.product.PriceHandler;

import java.util.List;

public class ItemPriceProcessor {

    private ItemHandler itemHandler;
    private PriceHandler priceHandler;

    public ItemPriceProcessor(ItemHandler itemHandler, PriceHandler priceHandler) {
        this.itemHandler = itemHandler;
        this.priceHandler = priceHandler;
    }

    public void checkAgainstDatabase(List<ParsedItemDto> dataSet, StoreUrl storeUrl) {

        dataSet.forEach(parsedItemDto -> {
            Item item = itemHandler.findItemByUpc(parsedItemDto);
            if (item != null) {
                itemHandler.updateItemCategories(item, storeUrl.getCategories());
                Price lastPrice = priceHandler.fetchLastPriceForItem(item);
                if (lastPrice == null) {
                    priceHandler.firstPriceForItem(parsedItemDto, item);
                } else if (lastPrice.getPrice() != parsedItemDto.getPrice()) {
                    double delta = Math.abs(lastPrice.getPrice() - parsedItemDto.getPrice()) / ((lastPrice.getPrice() + parsedItemDto.getPrice()) / 2);
                    priceHandler.changedPriceForItem(parsedItemDto, item, delta);
                }
            } else {
                itemHandler.createNewItemWithPrice(parsedItemDto, storeUrl);
            }
        });
    }
}
