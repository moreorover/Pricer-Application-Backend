package martin.dev.pricer.data.model.dto.child;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ItemDtoChild {

    private String upc;
    private String name;
    private String url;
    private String img;
    private double price;
    private double delta;
    private LocalDateTime foundTime;
    private String foundWhere;
}
