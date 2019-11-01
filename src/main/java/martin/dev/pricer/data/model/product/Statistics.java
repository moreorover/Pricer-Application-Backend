package martin.dev.pricer.data.model.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Statistics {

    private Double currentDelta;
    private Double averagePrice;

}
