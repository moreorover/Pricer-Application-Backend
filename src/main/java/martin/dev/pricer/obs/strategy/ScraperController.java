package martin.dev.pricer.obs.strategy;

import martin.dev.pricer.data.model.Store;
import martin.dev.pricer.data.model.Url;
import martin.dev.pricer.obs.ParserObserver;

import java.util.ArrayList;
import java.util.List;

public class ScraperController {

    private List<Scraper> scrapers = new ArrayList<Scraper>();

    private Store store;
    private Url url;
}
