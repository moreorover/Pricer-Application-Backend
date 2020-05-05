package martin.dev.pricer.flyway.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Price extends BaseEntity {

    private LocalDateTime foundTime;
    private double price;
    private double delta;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    public Price() {
    }
}
