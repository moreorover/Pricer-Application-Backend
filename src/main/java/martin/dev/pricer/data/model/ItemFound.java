package martin.dev.pricer.data.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class ItemFound extends BaseEntity {

    private String upc;
    private String title;
    private String url;
    private String img;
    private double price;
    private LocalDateTime foundAt;
}
