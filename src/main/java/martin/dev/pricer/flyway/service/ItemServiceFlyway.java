package martin.dev.pricer.flyway.service;

import martin.dev.pricer.flyway.model.Deal;
import martin.dev.pricer.flyway.model.Item;
import martin.dev.pricer.flyway.model.Price;
import martin.dev.pricer.flyway.repository.ItemRepositoryFlyway;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

@Service
public class ItemServiceFlyway {

    private final ItemRepositoryFlyway itemRepositoryFlyway;

    public ItemServiceFlyway(ItemRepositoryFlyway itemRepositoryFlyway) {
        this.itemRepositoryFlyway = itemRepositoryFlyway;
    }

    public Item fetchItemByUp(String upc) {
        return itemRepositoryFlyway.findItemByUpc(upc);
    }

    public void processParsedItemDto(ParsedItemDto parsedItemDto) {
        Item item = itemRepositoryFlyway.findItemByUpc(parsedItemDto.getUpc());

        if (item == null) {
            // if no such item with upc
            item = buildNewItem(parsedItemDto);
            itemRepositoryFlyway.save(item);
            return;
        }
        if (!item.compareToParsedItemDto(parsedItemDto)) {
            updateItem(item, parsedItemDto);
            item = itemRepositoryFlyway.save(item);
        }
        if (item.getPrice() > parsedItemDto.getPrice()){
            // if new price has gone down
            newItemPrice(item, parsedItemDto);
            itemDealExpired(item);
            newItemDeal(item, parsedItemDto);
            itemRepositoryFlyway.save(item);
            return;
        }
        if (item.getPrice() < parsedItemDto.getPrice()){
            // if new price has gone up
            newItemPrice(item, parsedItemDto);
            itemDealExpired(item);
            itemRepositoryFlyway.save(item);
        }
    }

    private Item buildNewItem(ParsedItemDto parsedItemDto) {
        Item item = new Item();
        item.setUpc(parsedItemDto.getUpc());
        item.setName(parsedItemDto.getTitle());
        item.setUrl(parsedItemDto.getUrl());
        item.setImg(parsedItemDto.getImg());
        item.setPrice(parsedItemDto.getPrice());
        item.setDelta(0.0);
        item.setFoundTime(parsedItemDto.getFoundTime());
        item.setFoundWhere(parsedItemDto.getUrlFound());
        item.setUrlObject(parsedItemDto.getUrlObject());

        Price price = new Price();
        price.setDelta(0.0);
        price.setPrice(parsedItemDto.getPrice());
        price.setFoundTime(parsedItemDto.getFoundTime());

        item.newPrice(price);

        return item;
    }

    private void updateItem(Item item, ParsedItemDto parsedItemDto) {
        item.setUpc(parsedItemDto.getUpc());
        item.setName(parsedItemDto.getTitle());
        item.setUrl(parsedItemDto.getUrl());
        item.setImg(parsedItemDto.getImg());
        item.setFoundWhere(parsedItemDto.getUrlFound());
        item.setUrlObject(parsedItemDto.getUrlObject());
    }

    private void newItemPrice(Item item, ParsedItemDto parsedItemDto) {
        Price price = new Price();
        price.setDelta(calculateDelta(item.getPrice(), parsedItemDto.getPrice()));
        price.setPrice(parsedItemDto.getPrice());
        price.setFoundTime(parsedItemDto.getFoundTime());
        item.newPrice(price);
    }

    private void newItemDeal(Item item, ParsedItemDto parsedItemDto) {
        Deal deal = new Deal();
        deal.setDealAvailable(true);
        deal.setFoundTime(parsedItemDto.getFoundTime());
        item.newDeal(deal);
    }

    private void itemDealExpired(Item item) {
        item.getDeals().stream()
                .filter(Deal::isDealAvailable)
                .forEach(deal -> deal.setDealAvailable(false));
    }

    public double calculateDelta(double oldPrice, double newPrice) {
        double delta = 100 * ((newPrice - oldPrice) / oldPrice);
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(delta));
    }

    public Item saveItem(Item item) {
        return this.itemRepositoryFlyway.save(item);
    }
}
