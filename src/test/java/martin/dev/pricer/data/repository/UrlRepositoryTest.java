package martin.dev.pricer.data.repository;

import martin.dev.pricer.data.model.Status;
import martin.dev.pricer.data.model.Url;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles({"test"})
class UrlRepositoryTest {

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Test
    public void checkIfDbIsPopulated() {
        List<Url> urls = urlRepository.findAll();
        assertTrue(urls.size() > 0);
        assertNotNull(urls.get(0).getStore());
        assertTrue(urls.get(0).getCategories().size() > 0);
    }

    @Test
    public void fetchListOfUrlsIfCheckedAtIsBeforeOrNullAndStatus() {
        LocalDateTime localDateTime = LocalDateTime.now().minusHours(2);
        Optional<Status> status = statusRepository.findById(3L);
        List<Url> urls = urlRepository.findAllByStatus(status.get());
        assertTrue(urls.size() > 0);

    }
}