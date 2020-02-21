package martin.dev.pricer.data.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.DecimalFormat;
import java.util.HashSet;
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

    public Item() {
    }

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

    public double getLastPrice() {
        return prices.stream()
                .max((price, t1) -> price.getFoundAt().compareTo(t1.getFoundAt()))
                .get().getPrice();
    }
}
