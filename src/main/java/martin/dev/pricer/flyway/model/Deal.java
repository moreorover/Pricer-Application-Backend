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
public class Deal extends BaseEntity {

    private boolean dealAvailable;
    private LocalDateTime foundTime;
    private boolean postedToDiscord;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    public Deal() {
    }
}
