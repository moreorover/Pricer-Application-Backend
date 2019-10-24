package martin.dev.pricer.scraper.parser.hsamuel;

import martin.dev.pricer.data.model.product.Item;
import martin.dev.pricer.scraper.parser.MainParser;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class HSamuelMain implements MainParser {

    private Document pageInJsoup;
    private Elements products;
    private List<Item> parsedItems = new ArrayList<>();

    public HSamuelMain(Document pageInJsoup) {
        this.pageInJsoup = pageInJsoup;
    }

    public void parseListOfAdElements() {
        products = pageInJsoup.select("div.product-tile.js-product-item");
    }

    public void parseElementsToItems() {
        products.forEach(element -> {
            //Item item = new Item();
            HSamuelAd hSamuelAd = new HSamuelAd(element);

            String title = hSamuelAd.parseTitle();
            String url = hSamuelAd.parseUrl();
            Double price = hSamuelAd.parsePrice();

            System.out.println(title);
            System.out.println(url);
            System.out.println(price);
        });
    }
}
