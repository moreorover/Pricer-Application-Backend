package martin.dev.pricer.data.model.store;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import martin.dev.pricer.data.model.BaseEntity;
import martin.dev.pricer.data.model.product.Category;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class StoreUrl extends BaseEntity {

    @Column(nullable = false)
    private String urlLink;
    private LocalDateTime lastChecked;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "store_id")
    private Store store;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            joinColumns = {@JoinColumn(name = "storeUrl_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")}
    )
    private Set<Category> categories = new HashSet<>();

}
