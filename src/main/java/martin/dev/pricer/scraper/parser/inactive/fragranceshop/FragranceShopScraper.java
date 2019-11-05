package martin.dev.pricer.scraper.parser.inactive.fragranceshop;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.model.store.StoreUrl;
import martin.dev.pricer.scraper.parser.Scraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FragranceShopScraper extends Scraper {

    @Autowired
    private FragranceShopParser fragranceShopParser;

    @Override
    public void scrapePages(StoreUrl storeUrl) {

    }

    @Override
    public String makeNextPageUrl(int pageNum) {
        return null;
    }
}
