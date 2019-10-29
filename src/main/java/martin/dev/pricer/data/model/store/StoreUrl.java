package martin.dev.pricer.data.model.store;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import martin.dev.pricer.data.model.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

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

}
