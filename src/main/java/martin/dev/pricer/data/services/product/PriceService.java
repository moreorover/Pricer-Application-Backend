package martin.dev.pricer.data.services.product;

import martin.dev.pricer.data.fabric.EntityFactory;
import martin.dev.pricer.data.model.product.Item;
import martin.dev.pricer.data.model.product.Price;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PriceService {

    private final DecimalFormat df = new DecimalFormat("#.##");

    private PriceRepository priceRepository;

    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public Price save(Price price) {
        return priceRepository.save(price);
    }

    public void changedPriceForItem(ParsedItemDto parsedItemDto, Item item, double delta, LocalDateTime localDateTime) {
        Price price = EntityFactory.createPrice(item, parsedItemDto, delta, localDateTime);
        priceRepository.save(price);
    }

    public Price fetchLastPriceForItem(Item item) {
        return priceRepository.findFirstByItemOrderByFoundAtDesc(item);
    }

    public double fetchLastPriceForItem(Set<Price> itemPrices) {
        List<Price> sorted = itemPrices.stream()
                .sorted(Comparator.comparing(Price::getFoundAt))
                .collect(Collectors.toList());

        return sorted.get(sorted.size() - 1).getPrice();
    }

    public List<Price> fetchPrices(Item item) {
        return priceRepository.findAllByItem(item);
    }

    public Price fetchMinPrice(Item item) {
        return priceRepository.findFirstByItemOrderByPriceAsc(item);
    }

    public double fetchMinPrice(Set<Price> prices) {
        return prices.stream()
                .mapToDouble(Price::getPrice)
                .min()
                .orElse(Double.NaN);
    }

    public Price fetchMaxPrice(Item item) {
        return priceRepository.findFirstByItemOrderByPriceDesc(item);
    }

    public double fetchMaxPrice(Set<Price> prices) {
        return prices.stream()
                .mapToDouble(Price::getPrice)
                .max()
                .orElse(Double.NaN);
    }

    public double fetchAvgPrice(Item item) {
        List<Price> prices = fetchPrices(item);
        double avgPrice = prices.stream()
                .mapToDouble(Price::getPrice)
                .average()
                .orElse(Double.NaN);
        return Double.parseDouble(df.format(avgPrice));
    }

    public double fetchAvgPrice(Set<Price> prices) {
        double avgPrice = prices.stream()
                .mapToDouble(Price::getPrice)
                .average()
                .orElse(0.0);
        return Double.parseDouble(df.format(avgPrice));
    }

    public double fetchAvgDelta(Item item) {
        List<Price> prices = fetchPrices(item);
        double avgDelta = prices.stream()
                .mapToDouble(Price::getDelta)
                .average()
                .orElse(Double.NaN);
        return Double.parseDouble(df.format(avgDelta));
    }

    public double fetchAvgDelta(Set<Price> prices) {
        double avgDelta = prices.stream()
                .mapToDouble(Price::getDelta)
                .filter(x -> x != 0)
                .average()
                .orElse(0.0);
        return Double.parseDouble(df.format(avgDelta));
    }
}
