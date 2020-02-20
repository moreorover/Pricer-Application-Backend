package martin.dev.pricer.data.model.mongo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Price {

    private Double price;
    private Double delta;
    private LocalDateTime foundAt;

    public double compareTo(Price otherPrice) {
        return otherPrice.price - this.price;
    }
}
