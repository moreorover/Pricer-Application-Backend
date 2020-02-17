package martin.dev.pricer.data.model.mongo.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document
public class Deal extends BaseEntity {

    private Item item;
    private Set<Category> categories = new HashSet<>();
    private Store store;

}
