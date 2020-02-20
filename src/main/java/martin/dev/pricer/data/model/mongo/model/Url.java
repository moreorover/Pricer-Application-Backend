package martin.dev.pricer.data.model.mongo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class Url extends BaseEntity {

    private String url;
    private LocalDateTime lastChecked;
    private Status status;

    private Set<Category> categories;

    public void updateLastCheckedToNow() {
        this.setLastChecked(LocalDateTime.now());
    }

    public void updateStatusTo(Status status) {
        this.setStatus(status);
    }

    public boolean isReadyToScrape() {
        return this.lastChecked.isBefore(LocalDateTime.now().minusHours(2)) && this.status.equals(Status.READY);
    }


}
