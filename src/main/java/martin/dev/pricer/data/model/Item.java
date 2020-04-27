package martin.dev.pricer.data.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Document
@Data
public class Item extends BaseEntity {

    private String upc;
    private String title;
    private String url;
    private String img;

    private Set<Price> prices = new HashSet<>();
    private Set<Category> categories = new HashSet<>();
    @DBRef
    private Store store;

    private String urlFound;

    public Item(String upc, String title, String url, String img, Set<Price> prices, Set<Category> categories, Store store, String urlFound) {
        this.upc = upc;
        this.title = title;
        this.url = url;
        this.img = img;
        this.prices = prices;
        this.categories = categories;
        this.store = store;
        this.urlFound = urlFound;
    }

    public double getMaxPrice() {
        return this.prices.stream()
                .mapToDouble(Price::getPrice)
                .max()
                .orElse(Double.NaN);
    }

    public double getMinPrice() {
        return this.prices.stream()
                .mapToDouble(Price::getPrice)
                .min()
                .orElse(Double.NaN);
    }

    public double getAvgPrice() {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        double avgPrice = prices.stream()
                .mapToDouble(Price::getPrice)
                .average()
                .orElse(0.0);
        return Double.parseDouble(decimalFormat.format(avgPrice));
    }

    public double getMaxDelta() {
        return this.prices.stream()
                .mapToDouble(Price::getDelta)
                .max()
                .orElse(Double.NaN);
    }

    public double getMinDelta() {
        return this.prices.stream()
                .mapToDouble(Price::getDelta)
                .min()
                .orElse(Double.NaN);
    }

    public double getAvgDelta() {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        double avgPrice = prices.stream()
                .mapToDouble(Price::getDelta)
                .average()
                .orElse(0.0);
        return Double.parseDouble(decimalFormat.format(avgPrice));
    }

    public double getLastPrice() {
        return prices.stream()
                .max(Comparator.comparing(Price::getFoundAt))
                .get().getPrice();
    }

    public double getLastDelta() {
        return prices.stream()
                .max(Comparator.comparing(Price::getFoundAt))
                .get().getDelta();
    }

    public boolean equalsToParsedItem(ParsedItemDto parsedItemDto) {
        return this.title.equals(parsedItemDto.getTitle()) &&
                this.url.equals(parsedItemDto.getUrl()) &&
                this.img.equals(parsedItemDto.getImg()) &&
                this.urlFound.equals(parsedItemDto.getUrlFound());
    }

    public void update(ParsedItemDto parsedItemDto) {
        this.title = parsedItemDto.getTitle();
        this.url = parsedItemDto.getUrl();
        this.img = parsedItemDto.getImg();
        this.urlFound = parsedItemDto.getUrlFound();
    }
}
