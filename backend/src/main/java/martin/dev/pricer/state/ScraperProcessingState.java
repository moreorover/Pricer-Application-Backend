package martin.dev.pricer.state;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.service.ItemService;

@Slf4j
public class ScraperProcessingState extends ScraperState {

    private ItemService itemService;

    public ScraperProcessingState(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    public void processItems(Scraper scraper) {
        log.info("Found this many valid items: " + scraper.getItems().size());

        scraper.getItems().forEach(parsedItemDto -> itemService.processParsedItemDto(parsedItemDto));
        scraper.getItems().clear();
        scraper.changeState(State.ParsingHtml);
        scraper.nextPage();
    }
}
