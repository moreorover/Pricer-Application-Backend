package martin.dev.pricer.flyway.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
public class Item extends BaseEntity{

    private String upc;
    private String name;
    private String url;
    private String img;
    private double price;
    private double delta;
    private LocalDateTime foundTime;
    private String foundWhere;

    @ManyToOne
    @JoinColumn(name = "url_id")
    private Url urlObject;

    @OneToMany(mappedBy = "item")
    private Set<Deal> deals;
}
