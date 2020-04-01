package martin.dev.pricer.obs.strategy;

import martin.dev.pricer.data.service.ItemService;

public class HSamuelScraper extends Scraper {


    public HSamuelScraper(ScraperSubject scraperSubject, ParserHandler parserHandler, ItemService itemService, int startPage) {
        super(scraperSubject, parserHandler, itemService, startPage);
    }
}
