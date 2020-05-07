package martin.dev.pricer.data.repository;

import martin.dev.pricer.data.model.Status;
import martin.dev.pricer.data.model.Store;
import martin.dev.pricer.data.model.Url;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles({"test"})
class UrlRepositoryTest {

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Test
    public void addUrls() {
        Status readyStatus = statusRepository.findStatusByStatus("Ready");
        Store store = storeRepository.getOne(1L);

        Url url = new Url();
        url.setStatus(readyStatus);
        url.setStore(store);
        url.setCheckedAt(LocalDateTime.now());
        url.setUrl("now");

        urlRepository.save(url);
    }

    @Test
    public void addUrlss() {
        Status readyStatus = statusRepository.findStatusByStatus("Ready");
        Store store = storeRepository.getOne(1L);

        Url url = new Url();
        url.setStatus(readyStatus);
        url.setStore(store);
        url.setCheckedAt(LocalDateTime.now().plusDays(10));
        url.setUrl("now+10");

        urlRepository.save(url);
    }

    @Test
    public void addUrlsNull() {
        Status readyStatus = statusRepository.findStatusByStatus("Ready");
        Store store = storeRepository.getOne(1L);

        Url url = new Url();
        url.setStatus(readyStatus);
        url.setStore(store);
        url.setCheckedAt(null);
        url.setUrl("null");

        urlRepository.save(url);
    }

    @Test
    public void check() {
        Status readyStatus = statusRepository.findStatusByStatus("Ready");
        List<Url> urls = urlRepository.findAllByStatusAndCheckedAtIsBefore(readyStatus, LocalDateTime.now().plusDays(2));
        List<Url> urls2 = urlRepository.findAllByStatusAndCheckedAtIsBeforeOrStatusAndCheckedAtIsNull(readyStatus, LocalDateTime.now().plusDays(20), readyStatus);

        assertEquals(1, urls.size());
        assertEquals(3, urls2.size());
    }

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