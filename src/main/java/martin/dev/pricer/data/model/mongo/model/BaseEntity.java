package martin.dev.pricer.data.model.mongo.model;

import org.springframework.data.annotation.Id;

public abstract class BaseEntity {

    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
