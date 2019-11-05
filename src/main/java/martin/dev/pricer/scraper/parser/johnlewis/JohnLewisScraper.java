package martin.dev.pricer.scraper.parser.johnlewis;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.model.store.StoreUrl;
import martin.dev.pricer.scraper.parser.Scraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JohnLewisScraper extends Scraper {

    @Autowired
    private JohnLewisParser johnLewisParser;

    @Override
    public void scrapePages(StoreUrl storeUrl) {

    }

    @Override
    public String makeNextPageUrl(int pageNum) {
        return null;
    }
}
