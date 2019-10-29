package martin.dev.pricer.data.model.dto.parse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ParsedItemDto {
    private String title;
    private String url;
    private String img;
    private String upc;
    private double price;
}
