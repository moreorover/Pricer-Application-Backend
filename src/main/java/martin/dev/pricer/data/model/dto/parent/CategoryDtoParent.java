package martin.dev.pricer.data.model.dto.parent;

import lombok.Getter;
import lombok.Setter;
import martin.dev.pricer.data.model.dto.child.CategoryDtoChild;
import martin.dev.pricer.data.model.dto.child.UrlDtoChild;

import java.util.Set;

@Getter
@Setter
public class CategoryDtoParent extends CategoryDtoChild {

    private Set<UrlDtoChild> urls;
}
