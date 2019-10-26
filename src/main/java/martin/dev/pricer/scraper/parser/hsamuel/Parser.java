package martin.dev.pricer.scraper.parser.hsamuel;

import martin.dev.pricer.data.services.product.ItemRepository;
import martin.dev.pricer.data.services.product.PriceRepository;
import martin.dev.pricer.scraper.client.HttpClient;
import org.jsoup.nodes.Document;

public class Parser {

    private ItemRepository itemRepository;
    private PriceRepository priceRepository;

    public Parser(ItemRepository itemRepository, PriceRepository priceRepository) {
        this.itemRepository = itemRepository;
        this.priceRepository = priceRepository;
    }

    public void parse(){
        Document pageContentInJsoup = HttpClient.readContentInJsoupDocument("https://www.hsamuel.co.uk/webstore/l/watches/recipient%7Chim/?icid=hs-nv-watches-him&Pg=1");
        HSamuelPage hSamuelMain = new HSamuelPage(pageContentInJsoup, itemRepository, priceRepository);
        hSamuelMain.parseMaxPageNum();
        hSamuelMain.parseCurrentPageNum();

        while (hSamuelMain.getCurrentPageNum() < hSamuelMain.getMaxPageNum() + 1){

        }

        hSamuelMain.parseListOfAdElements();
        hSamuelMain.parseElementsToItems();
    }
}
