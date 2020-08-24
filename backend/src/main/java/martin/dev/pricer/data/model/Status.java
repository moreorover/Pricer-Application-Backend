package martin.dev.pricer.data.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@ToString
public class Status extends BaseEntity {

    private String status;

    public Status() {
    }
}
