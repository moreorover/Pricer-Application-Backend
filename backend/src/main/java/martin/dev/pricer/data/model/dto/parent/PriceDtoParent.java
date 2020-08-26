package martin.dev.pricer.data.model.dto.parent;

import lombok.Getter;
import lombok.Setter;
import martin.dev.pricer.data.model.Item;
import martin.dev.pricer.data.model.dto.child.PriceDtoChild;

@Getter
@Setter
public class PriceDtoParent extends PriceDtoChild {

    private Item item;

}
