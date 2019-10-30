package martin.dev.pricer.data.services.product;

import martin.dev.pricer.scraper.model.ParsedItemDto;
import martin.dev.pricer.data.model.product.Item;
import martin.dev.pricer.data.model.product.Price;

import java.time.LocalDateTime;

public class PriceHandler {

    private PriceRepository priceRepository;

    public PriceHandler(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public void firstPriceForItem(ParsedItemDto parsedItemDto, Item item){
        Price price = new Price();
        price.setPrice(parsedItemDto.getPrice());
        price.setDelta(0.0);
        price.setItem(item);
        price.setFoundAt(LocalDateTime.now());
        priceRepository.save(price);
    }

    public void changedPriceForItem(ParsedItemDto parsedItemDto, Item item, double delta){
        Price price = new Price();
        price.setPrice(parsedItemDto.getPrice());
        price.setDelta(delta);
        price.setItem(item);
        price.setFoundAt(LocalDateTime.now());
        priceRepository.save(price);
    }

    public Price fetchLastPriceForItem(Item item){
        return priceRepository.findFirstByItemOrderByFoundAtDesc(item);
    }
}
