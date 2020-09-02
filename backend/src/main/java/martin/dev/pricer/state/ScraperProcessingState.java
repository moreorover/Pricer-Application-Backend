package martin.dev.pricer.state;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.service.ItemService;

@Slf4j
public class ScraperProcessingState implements ScraperState {

    private ItemService itemService;

    public ScraperProcessingState(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    public void fetchUrl(Scraper scraper) {

    }

    @Override
    public void fetchHtml(Scraper scraper) {

    }

    @Override
    public void validateResponse(Scraper scraper) {

    }

    @Override
    public void parseResponseToAds(Scraper scraper) {

    }

    @Override
    public void validateAds(Scraper scraper) {

    }

    @Override
    public void parseAdsToItems(Scraper scraper) {

    }

    @Override
    public void validateItems(Scraper scraper) {

    }

    @Override
    public void processItems(Scraper scraper) {
        log.info("Found this many valid items: " + scraper.getItems().size());

        scraper.getItems().forEach(parsedItemDto -> itemService.processParsedItemDto(parsedItemDto));
        scraper.getItems().clear();
        scraper.changeState(State.ParsingHtml);
        scraper.nextPage();
    }

    @Override
    public void writeItems(Scraper scraper) {

    }

    @Override
    public void sendItems(Scraper scraper) {

    }

    @Override
    public void nextPage(Scraper scraper) {

    }
}
