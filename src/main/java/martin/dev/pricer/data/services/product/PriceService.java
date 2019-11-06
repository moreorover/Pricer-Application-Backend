package martin.dev.pricer.data.services.product;

import martin.dev.pricer.data.fabric.EntityFactory;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import martin.dev.pricer.data.model.product.Item;
import martin.dev.pricer.data.model.product.Price;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PriceService {

    private final DecimalFormat df = new DecimalFormat("#.##");

    private PriceRepository priceRepository;

    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public void changedPriceForItem(ParsedItemDto parsedItemDto, Item item, double delta, LocalDateTime localDateTime){
        Price price = EntityFactory.createPrice(item, parsedItemDto, delta, localDateTime);
        priceRepository.save(price);
    }

    public Price fetchLastPriceForItem(Item item){
        return priceRepository.findFirstByItemOrderByFoundAtDesc(item);
    }

    public List<Price> fetchPrices(Item item) {
        return priceRepository.findAllByItem(item);
    }

    public Price fetchMinPrice(Item item){
        return priceRepository.findFirstByItemOrderByPriceAsc(item);
    }

    public double fetchMinPrice(List<Price> prices){
        return prices.stream()
                .mapToDouble(Price::getPrice)
                .min()
                .orElse(Double.NaN);
    }

    public Price fetchMaxPrice(Item item){
        return priceRepository.findFirstByItemOrderByPriceDesc(item);
    }

    public double fetchMaxPrice(List<Price> prices){
        return prices.stream()
                .mapToDouble(Price::getPrice)
                .max()
                .orElse(Double.NaN);
    }

    public double fetchAvgPrice(Item item){
        List<Price> prices = fetchPrices(item);
        double avgPrice = prices.stream()
                .mapToDouble(Price::getPrice)
                .average()
                .orElse(Double.NaN);
        return Double.parseDouble(df.format(avgPrice));
    }

    public double fetchAvgPrice(List<Price> prices){
        double avgPrice = prices.stream()
                .mapToDouble(Price::getPrice)
                .average()
                .orElse(0.0);
        return Double.parseDouble(df.format(avgPrice));
    }

    public double fetchAvgDelta(Item item){
        List<Price> prices = fetchPrices(item);
        double avgDelta = prices.stream()
                .mapToDouble(Price::getDelta)
                .average()
                .orElse(Double.NaN);
        return Double.parseDouble(df.format(avgDelta));
    }

    public double fetchAvgDelta(List<Price> prices){
        double avgDelta = prices.stream()
                .mapToDouble(Price::getDelta)
                .filter(x -> x!=0)
                .average()
                .orElse(0.0);
        return Double.parseDouble(df.format(avgDelta));
    }
}
