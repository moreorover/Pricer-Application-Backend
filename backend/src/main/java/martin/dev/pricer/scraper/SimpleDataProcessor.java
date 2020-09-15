package martin.dev.pricer.scraper;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.service.ItemService;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j
public class SimpleDataProcessor extends DataProcessor {

    private final ItemService itemService;

    public SimpleDataProcessor(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    public void processAdsToItems(Scraper scraper) {
        scraper.getAds().stream()
                .map(element -> {
                    ParsedItemDto parsedItemDto = new ParsedItemDto();
                    parsedItemDto.setFoundTime(LocalDateTime.now());
                    parsedItemDto.setTitle(scraper.getParser().parseAdTitle(element));
                    parsedItemDto.setUrl(scraper.getParser().parseAdUrl(element));
                    parsedItemDto.setImg(scraper.getParser().parseAdImage(element));
                    parsedItemDto.setUpc(scraper.getParser().parseAdUpc(element));
                    parsedItemDto.setPrice(scraper.getParser().parseAdPrice(element));
                    parsedItemDto.setUrlFound(scraper.getCurrentPageUrl());
                    parsedItemDto.setUrlObject(scraper.getUrl());
                    return parsedItemDto;
                })
                .filter(ParsedItemDto::isValid)
                .collect(Collectors.toCollection(scraper::getItems));
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
    }
}
