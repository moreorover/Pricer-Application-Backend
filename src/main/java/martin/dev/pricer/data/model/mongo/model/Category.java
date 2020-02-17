package martin.dev.pricer.data.model.mongo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document
@Data
@AllArgsConstructor
public class Category extends BaseEntity{

    private String name;
}
