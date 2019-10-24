package martin.dev.pricer.data.model.store;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import martin.dev.pricer.data.model.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class StoreUrl extends BaseEntity {

    @Column(nullable = false)
    private String urlLink;
    private LocalDateTime lastChecked;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "store_id")
    private Store store;

//    @JsonProperty
//    public Long getStoreId() {
//        return store == null ? null : store.getId();
//    }

}
