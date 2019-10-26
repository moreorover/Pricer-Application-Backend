package martin.dev.pricer.data.model.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import martin.dev.pricer.data.model.BaseEntity;
import martin.dev.pricer.data.model.store.Store;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Item extends BaseEntity {

    private String upc;
    private String title;
    private String url;
    private String img;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private Set<Price> prices = new HashSet<>();

    @ManyToMany
    @JoinTable(
            joinColumns = {@JoinColumn(name = "item_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")}
    )
    private Set<Category> categories = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

}
