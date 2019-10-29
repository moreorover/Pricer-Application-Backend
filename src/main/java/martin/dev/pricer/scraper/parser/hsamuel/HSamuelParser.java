package martin.dev.pricer.scraper.parser.hsamuel;

import martin.dev.pricer.data.model.store.StoreUrl;
import martin.dev.pricer.data.services.product.ItemRepository;
import martin.dev.pricer.data.services.product.PriceRepository;
import martin.dev.pricer.data.services.store.StoreUrlRepository;
import martin.dev.pricer.scraper.client.HttpClient;
import org.jsoup.nodes.Document;

import java.time.LocalDateTime;

public class HSamuelParser {

    private ItemRepository itemRepository;
    private PriceRepository priceRepository;
    private StoreUrlRepository storeUrlRepository;
    private StoreUrl storeUrl;

    public HSamuelParser(ItemRepository itemRepository, PriceRepository priceRepository, StoreUrlRepository storeUrlRepository, StoreUrl storeUrl) {
        this.itemRepository = itemRepository;
        this.priceRepository = priceRepository;
        this.storeUrlRepository = storeUrlRepository;
        this.storeUrl = storeUrl;
    }

    public void parse(){
        Document pageContentInJsoup = HttpClient.readContentInJsoupDocument(storeUrl.getUrlLink());
        HSamuelPage hSamuelMain = new HSamuelPage(pageContentInJsoup);
        hSamuelMain.parseMaxPageNum();
        int max = hSamuelMain.getMaxPageNum();

        for (int i = 1; i < max + 1; i++){
            String full = storeUrl.getUrlLink();
            String[] x = full.split("Pg=");
            full = x[0] + "Pg=" + i;

            System.out.println(full);
            Document document = HttpClient.readContentInJsoupDocument(full);
            HSamuelPage samuelPage = new HSamuelPage(itemRepository, priceRepository, document);
            samuelPage.parseListOfAdElements();
            if (samuelPage.getProducts().size() > 0){
                samuelPage.parseElementsToItems();
            }
        }

        storeUrl.setLastChecked(LocalDateTime.now());
        storeUrlRepository.save(storeUrl);
    }
}
