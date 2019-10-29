package martin.dev.pricer.data.model.dto.parse;

import martin.dev.pricer.data.model.store.Store;

public class ParsedItemDto {
    private String title;
    private String url;
    private String img;
    private String upc;
    private double price;
    private Store store;

    public ParsedItemDto(String title, String url, String img, String upc, double price) {
        this.title = title;
        this.url = url;
        this.img = img;
        this.upc = upc;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getImg() {
        return img;
    }

    public String getUpc() {
        return upc;
    }

    public double getPrice() {
        return price;
    }

    public Store getStore() {
        return store;
    }
}
