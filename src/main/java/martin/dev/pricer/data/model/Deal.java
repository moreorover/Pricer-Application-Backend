package martin.dev.pricer.data.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Document
@Data
public class Deal extends BaseEntity {

    private Item item;
    private Set<Category> categories;
    private Store store;
    private LocalDateTime dealFound;
    private boolean available;

    public Deal(Item item, Set<Category> categories, Store store, LocalDateTime dealFound, boolean available) {
        this.item = item;
        this.categories = categories;
        this.store = store;
        this.dealFound = dealFound;
        this.available = available;
    }
}
