package martin.dev.pricer.data.model.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import martin.dev.pricer.data.model.BaseEntity;
import martin.dev.pricer.data.model.store.StoreUrl;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Category extends BaseEntity {

    private String name;

    @ManyToMany(mappedBy = "categories")
    private Set<Item> items = new HashSet<>();

    @ManyToMany(mappedBy = "categories")
    private Set<StoreUrl> storeUrls = new HashSet<>();

}
