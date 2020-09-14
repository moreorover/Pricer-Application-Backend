package martin.dev.pricer.scraper;

import martin.dev.pricer.data.model.Status;
import martin.dev.pricer.data.model.Url;
import martin.dev.pricer.data.service.StatusService;
import martin.dev.pricer.data.service.UrlService;

import java.time.LocalDateTime;

public class RepoDataWriter extends DataWriter {

    private final StatusService statusService;
    private final UrlService urlService;

    public RepoDataWriter(StatusService statusService, UrlService urlService) {
        this.statusService = statusService;
        this.urlService = urlService;
    }

    @Override
    public void write(Scraper scraper) {
        Status statusReady = this.statusService.findStatusByStatus("Ready");
        this.urlService.updateUrlLastCheckedAtAndStatus(scraper.getUrl(), LocalDateTime.now(), statusReady);
    }
}
