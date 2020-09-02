package martin.dev.pricer.data.service;

import martin.dev.pricer.data.model.Status;
import martin.dev.pricer.data.model.Url;
import martin.dev.pricer.data.repository.UrlRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UrlService {

    private UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    // TODO test this as it returns incorrect dataset
    public List<Url> fetchUrlByStatus(Status status) {
        return this.urlRepository.findAllByStatus(status);
    }

    public List<Url> fetchUrlsByStatusAndCheckedAtBefore(Status status, LocalDateTime localDateTime) {
        return this.urlRepository.findAllByStatusAndCheckedAtIsBeforeOrStatusAndCheckedAtIsNull(status, localDateTime, status);
    }

    public Url fetchUrlByStatusAndCheckedAtBefore(Status status, LocalDateTime localDateTime) {
        return this.urlRepository.findFirstByStatusAndCheckedAtIsBeforeOrStatusAndCheckedAtIsNullOrderByCheckedAtAsc(status, localDateTime, status);
    }

    public void updateUrlLastCheckedAtAndStatus(Url url, LocalDateTime checkedAt, Status status) {
        url.setCheckedAt(checkedAt);
        url.setStatus(status);
        this.urlRepository.save(url);
    }
}
