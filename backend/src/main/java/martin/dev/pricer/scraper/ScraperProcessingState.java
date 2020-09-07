package martin.dev.pricer.scraper;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.service.ItemService;

import java.util.Random;

@Slf4j
public class ScraperProcessingState extends ScraperState {

    private final ItemService itemService;

    public ScraperProcessingState(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    public void processItems(Scraper scraper) {
        log.info("Found this many valid items: " + scraper.getItems().size());
//        Random r = new Random();
//        if (1 + r.nextInt((10 - 1) + 1) <= 2) {
//            System.out.println(parsedItemDto.toString());
//        }
        scraper.getItems().forEach(itemService::processParsedItemDto);
        scraper.getItems().clear();
        scraper.changeState(State.ParsingHtml);
        scraper.nextPage();
    }
}
