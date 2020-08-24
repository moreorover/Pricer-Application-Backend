package martin.dev.pricer.data.model.dto.parent;

import lombok.Getter;
import lombok.Setter;
import martin.dev.pricer.data.model.Status;
import martin.dev.pricer.data.model.dto.child.CategoryDtoChild;
import martin.dev.pricer.data.model.dto.child.StoreDtoChild;
import martin.dev.pricer.data.model.dto.child.UrlDtoChild;

import java.util.Set;

@Getter
@Setter
public class UrlDtoParent extends UrlDtoChild {

    private StoreDtoChild store;
    private Status status;
    private Set<CategoryDtoChild> categories;
}
