package martin.dev.pricer.flyway.service;

import martin.dev.pricer.flyway.model.Deal;
import martin.dev.pricer.flyway.model.Item;
import martin.dev.pricer.flyway.model.Url;
import martin.dev.pricer.flyway.repository.ItemRepositoryFlyway;
import martin.dev.pricer.flyway.repository.UrlRepositoryFlyway;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles({"test"})
class ItemServiceFlywayTest {

    @Autowired
    private UrlRepositoryFlyway urlRepositoryFlyway;

    @Autowired
    private ItemServiceFlyway itemServiceFlyway;

    @Test
    public void testItemId1NothingChanged() {
        Optional<Url> url = urlRepositoryFlyway.findById(3L);

        ParsedItemDto parsedItemDto = new ParsedItemDto();
        parsedItemDto.setTitle("Plants vs Zombies Garden Warfare 2 PS4 Hits Game");
        parsedItemDto.setImg("//media.4rgos.it/s/Argos/8869559_R_SET?w=270&h=270&qlt=75&fmt.jpeg.interlaced=true");
        parsedItemDto.setPrice(6);
        parsedItemDto.setUpc("A_8869559");
        parsedItemDto.setUrl("https://www.argos.co.uk/product/8869559");
        parsedItemDto.setUrlObject(url.get());
        parsedItemDto.setFoundTime(LocalDateTime.now());
        parsedItemDto.setUrlFound("https://www.argos.co.uk/browse/technology/video-games-and-consoles/ps4/ps4-games/c:30037/opt/page:8");

        assertTrue(parsedItemDto.isValid());

        itemServiceFlyway.processParsedItemDto(parsedItemDto);

        Item item = itemServiceFlyway.fetchItemByUp(parsedItemDto.getUpc());
        assertNotNull(item);
        assertEquals(item.getName(), parsedItemDto.getTitle());
        assertEquals(item.getId(), 1L);
        assertEquals(4, item.getDeals().size());
        assertEquals(1, item.getDeals().stream().filter(Deal::isDealAvailable).count());
    }

    @Test
    public void testItemId2DetailsChangedNoPriceChange() {
        Optional<Url> url = urlRepositoryFlyway.findById(3L);

        ParsedItemDto parsedItemDto = new ParsedItemDto();
        parsedItemDto.setTitle("Plants vs Zombies Garden Warfare 2 PS4 Hits Game");
        parsedItemDto.setImg("updated image url");
        parsedItemDto.setPrice(6);
        parsedItemDto.setUpc("A_8869559");
        parsedItemDto.setUrl("https://www.argos.co.uk/product/8869559");
        parsedItemDto.setUrlObject(url.get());
        parsedItemDto.setFoundTime(LocalDateTime.now());
        parsedItemDto.setUrlFound("https://www.argos.co.uk/browse/technology/video-games-and-consoles/ps4/ps4-games/c:30037/opt/page:8");

        assertTrue(parsedItemDto.isValid());

        itemServiceFlyway.processParsedItemDto(parsedItemDto);

        Item item = itemServiceFlyway.fetchItemByUp(parsedItemDto.getUpc());
        assertNotNull(item);
        assertEquals(item.getName(), parsedItemDto.getTitle());
        assertEquals(item.getImg(), parsedItemDto.getImg());
        assertEquals(item.getId(), 1L);
        assertEquals(4, item.getDeals().size());
        assertEquals(7, item.getPrices().size());
        assertTrue(item.isDealAvailable());
        assertEquals(1, item.getDeals().stream().filter(Deal::isDealAvailable).count());
    }

    @Test
    public void testItemId2PriceWentUp() {
        Optional<Url> url = urlRepositoryFlyway.findById(3L);

        ParsedItemDto parsedItemDto = new ParsedItemDto();
        parsedItemDto.setTitle("Plants vs Zombies Garden Warfare 2 PS4 Hits Game");
        parsedItemDto.setImg("//media.4rgos.it/s/Argos/8869559_R_SET?w=270&h=270&qlt=75&fmt.jpeg.interlaced=true");
        parsedItemDto.setPrice(12);
        parsedItemDto.setUpc("A_8869559");
        parsedItemDto.setUrl("https://www.argos.co.uk/product/8869559");
        parsedItemDto.setUrlObject(url.get());
        parsedItemDto.setFoundTime(LocalDateTime.now());
        parsedItemDto.setUrlFound("https://www.argos.co.uk/browse/technology/video-games-and-consoles/ps4/ps4-games/c:30037/opt/page:8");

        assertTrue(parsedItemDto.isValid());

        itemServiceFlyway.processParsedItemDto(parsedItemDto);

        Item item = itemServiceFlyway.fetchItemByUp(parsedItemDto.getUpc());
        assertNotNull(item);
        assertEquals(item.getName(), parsedItemDto.getTitle());
        assertEquals(item.getImg(), parsedItemDto.getImg());
        assertEquals(item.getId(), 1L);
        assertEquals(4, item.getDeals().size());
        assertEquals(8, item.getPrices().size());
        assertFalse(item.isDealAvailable());
        assertEquals(0, item.getDeals().stream().filter(Deal::isDealAvailable).count());
    }

    @Test
    public void testItemId2PriceWentDown() {
        Optional<Url> url = urlRepositoryFlyway.findById(3L);

        ParsedItemDto parsedItemDto = new ParsedItemDto();
        parsedItemDto.setTitle("Plants vs Zombies Garden Warfare 2 PS4 Hits Game");
        parsedItemDto.setImg("//media.4rgos.it/s/Argos/8869559_R_SET?w=270&h=270&qlt=75&fmt.jpeg.interlaced=true");
        parsedItemDto.setPrice(3);
        parsedItemDto.setUpc("A_8869559");
        parsedItemDto.setUrl("https://www.argos.co.uk/product/8869559");
        parsedItemDto.setUrlObject(url.get());
        parsedItemDto.setFoundTime(LocalDateTime.now());
        parsedItemDto.setUrlFound("https://www.argos.co.uk/browse/technology/video-games-and-consoles/ps4/ps4-games/c:30037/opt/page:8");

        assertTrue(parsedItemDto.isValid());

        itemServiceFlyway.processParsedItemDto(parsedItemDto);

        Item item = itemServiceFlyway.fetchItemByUp(parsedItemDto.getUpc());
        assertNotNull(item);
        assertEquals(item.getName(), parsedItemDto.getTitle());
        assertEquals(item.getImg(), parsedItemDto.getImg());
        assertEquals(item.getId(), 1L);
        assertEquals(5, item.getDeals().size());
        assertEquals(9, item.getPrices().size());
        assertTrue(item.isDealAvailable());
        assertEquals(1, item.getDeals().stream().filter(Deal::isDealAvailable).count());
    }

    @Test
    public void testParsedItemDto() {
        Optional<Url> url = urlRepositoryFlyway.findById(3L);

        ParsedItemDto parsedItemDto = new ParsedItemDto();
        parsedItemDto.setTitle("Plants vs Zombies Garden Warfare 2 PS4 Hits Game");
        parsedItemDto.setImg("//media.4rgos.it/s/Argos/8869559_R_SET?w=270&h=270&qlt=75&fmt.jpeg.interlaced=true");
        parsedItemDto.setPrice(3);
        parsedItemDto.setUpc("A_8869559");
        parsedItemDto.setUrl("https://www.argos.co.uk/product/8869559");
        parsedItemDto.setUrlObject(url.get());
        parsedItemDto.setFoundTime(LocalDateTime.now());
        parsedItemDto.setUrlFound("https://www.argos.co.uk/browse/technology/video-games-and-consoles/ps4/ps4-games/c:30037/opt/page:8");

        assertTrue(parsedItemDto.isValid());
    }
}