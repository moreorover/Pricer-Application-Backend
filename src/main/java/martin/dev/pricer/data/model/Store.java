package martin.dev.pricer.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

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

    @JsonIgnore
    @OneToMany(mappedBy = "store")
    private Set<Url> urls;

    public Store() {
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