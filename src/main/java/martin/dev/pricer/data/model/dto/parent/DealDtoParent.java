package martin.dev.pricer.data.model.dto.parent;

import lombok.Getter;
import lombok.Setter;
import martin.dev.pricer.data.model.dto.child.DealDtoChild;

@Getter
@Setter
public class DealDtoParent extends DealDtoChild {

    private ItemDtoParent item;
}
