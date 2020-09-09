package martin.dev.pricer.data.model;

import lombok.Getter;
import lombok.Setter;
import martin.dev.pricer.scraper.ParsedItemDto;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
public class Item extends BaseEntity {

    private String upc;
    private String name;
    private String url;
    private String img;
    private double price;
    private double delta;
    private LocalDateTime foundTime;
    private String foundWhere;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "url_id", nullable = false)
    private Url urlObject;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Deal> deals;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Price> prices;

    public Item() {
    }

    @Override
    public String toString() {
        return "Item{" +
                "upc='" + upc + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", img='" + img + '\'' +
                ", price=" + price +
                ", delta=" + delta +
                ", foundTime=" + foundTime +
                ", foundWhere='" + foundWhere + '\'' +
                ", deals=" + deals +
                ", prices=" + prices +
                '}';
    }

    public void newPrice(Price price) {
        price.setItem(this);

        if (this.getPrices() == null) {
            this.setPrices(new HashSet<Price>());
        }

        this.prices.add(price);
        this.price = price.getPrice();
        this.delta = price.getDelta();
    }

    public void newDeal(Deal deal) {
        deal.setItem(this);
        this.deals.add(deal);
    }

    public boolean isDealAvailable() {
        return this.deals.stream()
                .anyMatch(Deal::isDealAvailable);
    }

    public double getMaxPrice() {
        return this.prices.stream()
                .mapToDouble(Price::getPrice)
                .max()
                .orElse(0.0);
    }

    public double getMinPrice() {
        return this.prices.stream()
                .mapToDouble(Price::getPrice)
                .min()
                .orElse(0.0);
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
                .orElse(0.0);
    }

    public double getMinDelta() {
        return this.prices.stream()
                .mapToDouble(Price::getDelta)
                .min()
                .orElse(0.0);
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
                .max(Comparator.comparing(Price::getFoundTime))
                .get().getPrice();
    }

    public double getLastDelta() {
        return prices.stream()
                .max(Comparator.comparing(Price::getFoundTime))
                .get().getDelta();
    }

    public boolean compareToParsedItemDto(ParsedItemDto parsedItemDto) {
        // return TRUE if no changes found
        return upc.equals(parsedItemDto.getUpc()) &&
                name.equals(parsedItemDto.getTitle()) &&
                url.equals(parsedItemDto.getUrl()) &&
                Objects.equals(img, parsedItemDto.getImg()) &&
                foundWhere.equals(parsedItemDto.getUrlFound()) &&
                urlObject.equals(parsedItemDto.getUrlObject());
    }
}
