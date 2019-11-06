package martin.dev.pricer.data.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import martin.dev.pricer.data.model.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
    private double avgDelta;

    private double lastDelta;

    private LocalDateTime lastFound;

    private boolean deal;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id", nullable = false)
    private Item item;
}
