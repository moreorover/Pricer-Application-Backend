package martin.dev.pricer.flyway.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@Getter
@Setter
public class Store extends BaseEntity {

    private String name;
    private String url;
    private String logo;

    @OneToMany(mappedBy = "store")
    private Set<Url> urls;

    public Store() {
    }

    public Store(String name, String url, String logo) {
        this.name = name;
        this.url = url;
        this.logo = logo;
    }

    @Override
    public String toString() {
        return "Store{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", logo='" + logo + '\'' +
                '}';
    }
}