package martin.dev.pricer.scraper.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ParsedItemDto {
    private String title;
    private String url;
    private String img;
    private String upc;
    private double price;
    private String urlFound;

    public boolean isValid() {
        return !this.title.isBlank() && !this.title.isEmpty() &&
                !this.url.isBlank() && !this.url.isEmpty() &&
                !this.upc.isBlank() && !this.upc.isEmpty() &&
                this.price > 0;
    }
}
