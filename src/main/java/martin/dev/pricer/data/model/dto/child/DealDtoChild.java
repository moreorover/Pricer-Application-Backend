package martin.dev.pricer.data.model.dto.child;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DealDtoChild {

    private boolean dealAvailable;
    private LocalDateTime foundTime;
    private boolean postedToDiscord;
}
