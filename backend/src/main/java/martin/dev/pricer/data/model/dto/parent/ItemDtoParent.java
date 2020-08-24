package martin.dev.pricer.data.model.dto.parent;

import lombok.Getter;
import lombok.Setter;
import martin.dev.pricer.data.model.dto.child.DealDtoChild;
import martin.dev.pricer.data.model.dto.child.ItemDtoChild;
import martin.dev.pricer.data.model.dto.child.PriceDtoChild;

import java.util.Set;

@Getter
@Setter
public class ItemDtoParent extends ItemDtoChild {

    private UrlDtoParent urlObject;
    private Set<DealDtoChild> deals;
    private Set<PriceDtoChild> prices;
}
