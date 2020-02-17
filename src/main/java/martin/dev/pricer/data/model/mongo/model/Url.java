package martin.dev.pricer.data.model.mongo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import martin.dev.pricer.data.model.store.Status;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class Url extends BaseEntity{

    private String url;
    private LocalDateTime lastChecked;
    private Status status;

    private Set<Category> categories = new HashSet<>();
}
