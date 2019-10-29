package martin.dev.pricer.scraper.parser.hsamuel.parser;

import martin.dev.pricer.scraper.parser.PageParser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HSamuelPage implements PageParser {

    private Document pageInJsoup;
    private Elements adElements;
    private int maxPageNum;

    public HSamuelPage(Document pageInJsoup) {
        this.pageInJsoup = pageInJsoup;
    }

    public int getMaxPageNum() {
        return maxPageNum;
    }

    public Elements getAdElements() {
        return adElements;
    }

    public void parseListOfAdElements() {
        adElements = pageInJsoup.select("div.product-tile.js-product-item");
    }

    public void parseMaxPageNum(){
        Element paginationBlockElement = pageInJsoup.selectFirst("ol[class*=pageNumbers]");
        Elements paginationButtons = paginationBlockElement.select("li");
        Element lastPageElement = paginationButtons.get(5);
        String lastPageText = lastPageElement.text();
        maxPageNum = Integer.parseInt(lastPageText);
    }

//    public void parseElementsToItems() {
//        products.forEach(element -> {
//            HSamuelAdElement hSamuelAd = new HSamuelAdElement(element);
//
//            ItemPriceProcessor itemPriceProcessor = new ItemPriceProcessor(itemRepository, priceRepository);
//
//            itemPriceProcessor.checkAgainstDatabase(hSamuelAd.parseAll());
//        });
//    }
}
