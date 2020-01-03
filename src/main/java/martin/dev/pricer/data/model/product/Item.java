package martin.dev.pricer.data.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Price> prices = new HashSet<>();

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            joinColumns = {@JoinColumn(name = "item_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")}
    )
    private Set<Category> categories = new HashSet<>();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "store_id")
    private Store store;

    @JsonIgnore
    @OneToOne(mappedBy = "item", fetch = FetchType.EAGER)
    private Statistics statistics;

    @Override
    public String toString() {
        return "Item{" +
                "id='" + getId() + '\'' +
                "upc='" + upc + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
