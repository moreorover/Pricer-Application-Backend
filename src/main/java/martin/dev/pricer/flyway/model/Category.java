package martin.dev.pricer.flyway.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
public class Category extends BaseEntity{

    private String category;

    @ManyToMany(mappedBy = "categories")
    private Set<Url> urls;

    public Category() {
    }

    public Category(String category) {
        this.category = category;
    }
}