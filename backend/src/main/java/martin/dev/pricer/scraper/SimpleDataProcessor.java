package martin.dev.pricer.scraper;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.service.ItemService;

@Slf4j
public class SimpleDataProcessor extends DataProcessor {

    private final ItemService itemService;

    public SimpleDataProcessor(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    public void process(Scraper scraper) {
        log.info("Found this many valid items: " + scraper.getItems().size());
//        Random r = new Random();
//        if (1 + r.nextInt((10 - 1) + 1) <= 2) {
//            System.out.println(parsedItemDto.toString());
//        }
        scraper.getItems().forEach(itemService::processParsedItemDto);
        scraper.getItems().clear();
    }
}
