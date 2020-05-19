package martin.dev.pricer.data.model.dto.child;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PriceDtoChild {

    private LocalDateTime foundTime;
    private double price;
    private double delta;
}
