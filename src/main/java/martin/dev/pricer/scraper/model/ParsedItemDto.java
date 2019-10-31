package martin.dev.pricer.scraper.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class ParsedItemDto {
    private String title;
    private String url;
    private String img;
    private String upc;
    private double price;
}
