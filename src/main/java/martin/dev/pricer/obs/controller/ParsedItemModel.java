package martin.dev.pricer.obs.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ParsedItemModel {
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
