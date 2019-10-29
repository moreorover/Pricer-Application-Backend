package martin.dev.pricer.scraper.parser.hsamuel;

import martin.dev.pricer.data.fabric.product.ItemPriceProcessor;
import martin.dev.pricer.data.services.product.ItemRepository;
import martin.dev.pricer.data.services.product.PriceRepository;
import martin.dev.pricer.scraper.parser.PageParser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HSamuelPage implements PageParser {

    private Document pageInJsoup;
    private Elements products;
    private int maxPageNum;

    private ItemRepository itemRepository;
    private PriceRepository priceRepository;

    public HSamuelPage(Document pageInJsoup) {
        this.pageInJsoup = pageInJsoup;
    }

    public HSamuelPage(ItemRepository itemRepository, PriceRepository priceRepository, Document pageInJsoup) {
        this.pageInJsoup = pageInJsoup;
        this.itemRepository = itemRepository;
        this.priceRepository = priceRepository;
    }

    public int getMaxPageNum() {
        return maxPageNum;
    }

    public Elements getProducts() {
        return products;
    }

    public void parseListOfAdElements() {
        products = pageInJsoup.select("div.product-tile.js-product-item");
    }

    public void parseMaxPageNum(){
        Element paginationBlockElement = pageInJsoup.selectFirst("ol[class*=pageNumbers]");
        Elements paginationButtons = paginationBlockElement.select("li");
        Element lastPageElement = paginationButtons.get(5);
        String lastPageText = lastPageElement.text();
        maxPageNum = Integer.parseInt(lastPageText);
    }

    public void parseElementsToItems() {
        products.forEach(element -> {
            HSamuelAdElement hSamuelAd = new HSamuelAdElement(element);

            ItemPriceProcessor itemPriceProcessor = new ItemPriceProcessor(itemRepository, priceRepository);

            itemPriceProcessor.checkAgainstDatabase(hSamuelAd.parseAll());
        });
    }
}
