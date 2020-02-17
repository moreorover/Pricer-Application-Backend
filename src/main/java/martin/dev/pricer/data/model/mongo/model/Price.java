package martin.dev.pricer.data.model.mongo.model;

import java.time.LocalDateTime;

public class Price {

    private Double price;
    private Double delta;
    private LocalDateTime foundAt;

    public Price() {
    }

    public Price(Double price, Double delta, LocalDateTime foundAt) {
        this.price = price;
        this.delta = delta;
        this.foundAt = foundAt;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDelta() {
        return delta;
    }

    public void setDelta(Double delta) {
        this.delta = delta;
    }

    public LocalDateTime getFoundAt() {
        return foundAt;
    }

    public void setFoundAt(LocalDateTime foundAt) {
        this.foundAt = foundAt;
    }

    @Override
    public String toString() {
        return "Price{" +
                "price=" + price +
                ", delta=" + delta +
                ", foundAt=" + foundAt +
                '}';
    }
}
