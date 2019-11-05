package martin.dev.pricer.data.model.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import martin.dev.pricer.data.model.BaseEntity;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Statistics extends BaseEntity {

    private double lastPrice;
    private double minPrice;
    private double maxPrice;
    private double avgPrice;

    private double lastDelta;
    private double avgDelta;

    private LocalDateTime lastFound;

    private boolean deal;
}
