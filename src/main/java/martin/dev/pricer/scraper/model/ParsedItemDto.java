package martin.dev.pricer.scraper.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import martin.dev.pricer.flyway.model.Url;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ParsedItemDto {
    private String title;
    private String url;
    private String img;
    private String upc;
    private double price;
    private String urlFound;
    private LocalDateTime foundTime;
    private Url urlObject;

    public ParsedItemDto(String title, String url, String img, String upc, double price, String urlFound) {
        this.title = title;
        this.url = url;
        this.img = img;
        this.upc = upc;
        this.price = price;
        this.urlFound = urlFound;
    }

    public boolean isValid() {
        return !(
                this.title.isBlank() || this.title.isEmpty() ||
                        this.url.isBlank() || this.url.isEmpty() ||
                        this.upc.isBlank() || this.upc.isEmpty() ||
                        this.price <= 0
        );
    }
}
