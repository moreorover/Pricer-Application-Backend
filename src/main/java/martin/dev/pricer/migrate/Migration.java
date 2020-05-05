package martin.dev.pricer.migrate;

import martin.dev.pricer.data.model.Item;
import martin.dev.pricer.data.service.ItemService;
import martin.dev.pricer.flyway.model.Url;
import martin.dev.pricer.flyway.repository.UrlRepositoryFlyway;
import martin.dev.pricer.flyway.service.ItemServiceFlyway;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class Migration {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemServiceFlyway itemServiceFlyway;

    @Autowired
    private UrlRepositoryFlyway urlRepositoryFlyway;

    @Scheduled(fixedRate = 6000000 * 1000, initialDelay = 5 * 1000)
    public void migrate() {
        System.out.println("Started migration");

        for (int pageNumber = 1; pageNumber < 5000; pageNumber++) {
            List<Item> mongoItems = itemService.fetchItems(pageNumber);
            System.out.println("Found " + mongoItems.size() + " items in database.");
            System.out.println("Page number: " + pageNumber);
            System.out.println("Total Items processed: " + pageNumber * 100);
            mongoItems.forEach(mongoItem -> {
                @NotNull Optional<Url> sqlUrl = urlRepositoryFlyway.findById(57L);

                ParsedItemDto parsedItemDto = new ParsedItemDto();
                parsedItemDto.setTitle(mongoItem.getTitle());
                parsedItemDto.setUrl(mongoItem.getUrl());
                parsedItemDto.setImg(mongoItem.getImg());
                parsedItemDto.setUpc(mongoItem.getUpc());

                parsedItemDto.setUrlFound(mongoItem.getUrlFound());

                parsedItemDto.setUrlObject(sqlUrl.get());

                List<martin.dev.pricer.data.model.Price> mongoItemPrices = mongoItem.getPrices().stream().sorted(new Comparator<martin.dev.pricer.data.model.Price>() {
                    @Override
                    public int compare(martin.dev.pricer.data.model.Price o1, martin.dev.pricer.data.model.Price o2) {
                        return o1.getFoundAt().compareTo(o2.getFoundAt());
                    }
                }).collect(Collectors.toList());

                mongoItemPrices.forEach(price -> {
                    parsedItemDto.setPrice(price.getPrice());
                    parsedItemDto.setFoundTime(price.getFoundAt());

                    try {
                        itemServiceFlyway.processParsedItemDto(parsedItemDto);
                    } catch (DataIntegrityViolationException e) {
                        System.out.println("Exception for: " + mongoItem.getUpc());
                    }

                });
            });
        }


    }
}
