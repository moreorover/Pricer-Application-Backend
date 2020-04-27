package martin.dev.pricer.data.service;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.model.*;
import martin.dev.pricer.data.repository.DealRepository;
import martin.dev.pricer.data.repository.ItemRepository;
import martin.dev.pricer.discord.BotSendMessage;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@Getter
public class ItemService implements ItemServiceI {

    private ItemRepository itemRepository;
    private DealRepository dealRepository;
    private BotSendMessage botSendMessage;

    public ItemService(ItemRepository itemRepository, DealRepository dealRepository, BotSendMessage botSendMessage) {
        this.itemRepository = itemRepository;
        this.dealRepository = dealRepository;
        this.botSendMessage = botSendMessage;
    }

    @Override
    public Item findByUpc(String upc) {
        return this.itemRepository.findByUpc(upc);
    }

    @Override
    public Item save(Item newItem) {
        return this.itemRepository.save(newItem);
    }

    @Override
    public Item buildItemOfParsedData(ParsedItemDto parsedItemDto, Store store, Url url) {
        Set<Price> prices = new HashSet<>();
        prices.add(new Price(parsedItemDto.getPrice(),
                0.0,
                LocalDateTime.now()));

        return new Item(parsedItemDto.getUpc(),
                parsedItemDto.getTitle(),
                parsedItemDto.getUrl(),
                parsedItemDto.getImg(),
                prices,
                url.getCategories(),
                store,
                parsedItemDto.getUrlFound());
    }

    @Override
    public double calculateDelta(double oldPrice, double newPrice) {
        double delta = 100 * ((newPrice - oldPrice) / oldPrice);
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(delta));
    }

    @Override
    public Item newPrice(Item item, double newPrice) {
        double delta = calculateDelta(item.getLastPrice(), newPrice);
        Price newPriceObj = new Price(newPrice, delta, LocalDateTime.now());
        item.getPrices().add(newPriceObj);
        return item;
    }

    @Override
    public void processParsedItem(ParsedItemDto parsedItemDto, Store store, Url url) {
        if (parsedItemDto.isValid()) {

            Item dbItem = findByUpc(parsedItemDto.getUpc());

            if (dbItem == null) {
                // if item has not been recorded yet
                Item buildItem = buildItemOfParsedData(parsedItemDto, store, url);
                save(buildItem);
            } else if (dbItem.getLastPrice() > parsedItemDto.getPrice()) {
                // if price has dropped since last time
                newPrice(dbItem, parsedItemDto.getPrice());
                save(dbItem);
                Deal newDeal = new Deal(dbItem, dbItem.getCategories(), store, LocalDateTime.now(), true);
                dealRepository.save(newDeal);

                if (botSendMessage != null) {
                    botSendMessage.sendEmbedded(newDeal);
                }

            } else if (dbItem.getLastPrice() < parsedItemDto.getPrice()) {
                // if price has risen since last time
                newPrice(dbItem, parsedItemDto.getPrice());
                save(dbItem);

                Deal expiredDeal = dealRepository.findFirstByItem_IdAndAvailableOrderByDealFoundDesc(dbItem.getId(), true);
                if (expiredDeal != null){
                    expiredDeal.setAvailable(false);
                    expiredDeal.setItem(dbItem);
                    dealRepository.save(expiredDeal);
                }
            }
        }
    }
}
