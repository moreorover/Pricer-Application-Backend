package martin.dev.pricer.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@Document
public class Store extends BaseEntity {

    private String name;
    private String baseUrl;
    private String logo;

    private Set<Url> urls;

}
