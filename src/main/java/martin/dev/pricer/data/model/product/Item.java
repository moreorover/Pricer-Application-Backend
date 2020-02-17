package martin.dev.pricer.data.model.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import martin.dev.pricer.data.model.BaseEntity;
import martin.dev.pricer.data.model.store.Store;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Item extends BaseEntity {

    private String upc;
    private String title;
    private String url;
    private String img;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Price> prices = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            joinColumns = {@JoinColumn(name = "item_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")}
    )
    private Set<Category> categories = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToOne(mappedBy = "item", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Statistics statistics;

    @Override
    public String toString() {
        return "Item{" +
                "id='" + getId() + '\'' +
                "upc='" + upc + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public double getMaxPrice(){
        return this.prices.stream()
                .mapToDouble(Price::getPrice)
                .max()
                .orElse(Double.NaN);
    }

    public double getMinPrice(){
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
}
