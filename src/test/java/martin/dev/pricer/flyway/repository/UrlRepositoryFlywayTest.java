package martin.dev.pricer.flyway.repository;

import martin.dev.pricer.flyway.model.Status;
import martin.dev.pricer.flyway.model.Url;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles({"test"})
class UrlRepositoryFlywayTest {

    @Autowired
    private UrlRepositoryFlyway urlRepositoryFlyway;

    @Autowired
    private StatusRepositoryFlyway statusRepositoryFlyway;

    @Test
    public void checkIfDbIsPopulated() {
        List<Url> urls = urlRepositoryFlyway.findAll();
        assertTrue(urls.size() > 0);
        assertNotNull(urls.get(0).getStore());
        assertTrue(urls.get(0).getCategories().size() > 0);
    }

    @Test
    public void fetchListOfUrlsIfCheckedAtIsBeforeOrNullAndStatus() {
        LocalDateTime localDateTime = LocalDateTime.now().minusHours(2);
        Status status = statusRepositoryFlyway.findStatusByStatus("Disabled");
        List<Url> urls = urlRepositoryFlyway.findAllByCheckedAtBeforeOrCheckedAtIsNullAndStatusOrderByCheckedAtAsc(localDateTime, status);
        assertTrue(urls.size() > 0);

    }
}