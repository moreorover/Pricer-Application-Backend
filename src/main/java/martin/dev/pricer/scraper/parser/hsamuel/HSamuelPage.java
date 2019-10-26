package martin.dev.pricer.scraper.parser.hsamuel;

import martin.dev.pricer.data.fabric.product.ItemFabric;
import martin.dev.pricer.data.model.product.Item;
import martin.dev.pricer.data.services.product.ItemRepository;
import martin.dev.pricer.data.services.product.PriceRepository;
import martin.dev.pricer.scraper.parser.PageParser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class HSamuelPage implements PageParser {

    private Document pageInJsoup;
    private Elements products;
    private int maxPageNum;
    private int currentPageNum;

    private ItemRepository itemRepository;
    private PriceRepository priceRepository;

    public HSamuelPage(Document pageInJsoup, ItemRepository itemRepository, PriceRepository priceRepository) {
        this.pageInJsoup = pageInJsoup;
        this.itemRepository = itemRepository;
        this.priceRepository = priceRepository;
    }

    public int getMaxPageNum() {
        return maxPageNum;
    }

    public int getCurrentPageNum() {
        return currentPageNum;
    }

    public void parseListOfAdElements() {
        products = pageInJsoup.select("div.product-tile.js-product-item");
    }

    public void parseMaxPageNum(){
        Elements paginationElements = pageInJsoup.select("ol[class*=pageNumbers]");
        Element lastPageElement = paginationElements.get(5);
        String lastPageText = lastPageElement.text();
        maxPageNum = Integer.parseInt(lastPageText);
    }

    public void parseCurrentPageNum(){
        Element currentPageElement = pageInJsoup.selectFirst("span[class=current]");
        currentPageNum = Integer.parseInt(currentPageElement.text());
    }

    public void parseElementsToItems() {
        products.forEach(element -> {
            HSamuelAdElement hSamuelAd = new HSamuelAdElement(element);

            ItemFabric itemFabric = new ItemFabric(itemRepository, priceRepository);

            itemFabric.checkAgainstDatabase(hSamuelAd.parseAll());
        });
    }
}
