package martin.dev.pricer.scraper.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import martin.dev.pricer.data.model.Url;

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

    public boolean isValid() {
        return !(
                this.title == null || this.title.isBlank() || this.title.isEmpty() ||
                        this.url == null || this.url.isBlank() || this.url.isEmpty() ||
                        this.upc == null || this.upc.isBlank() || this.upc.isEmpty() ||
                        this.price <= 0
        );
    }

    @Override
    public String toString() {
        return "ParsedItemDto{" +
                "title='" + title + '\'' +
                ", price=" + price +
                '}';
    }
}
