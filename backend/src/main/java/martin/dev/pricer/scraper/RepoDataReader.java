package martin.dev.pricer.scraper;

import martin.dev.pricer.data.model.Status;
import martin.dev.pricer.data.model.Url;
import martin.dev.pricer.data.service.StatusService;
import martin.dev.pricer.data.service.UrlService;

import java.time.LocalDateTime;

public class RepoDataReader extends DataReader {

    private final StatusService statusService;
    private final UrlService urlService;

    public RepoDataReader(StatusService statusService, UrlService urlService) {
        this.statusService = statusService;
        this.urlService = urlService;
    }

    @Override
    public void fetchUrl(Scraper scraper) {
        Status statusReady = this.statusService.findStatusByStatus("Ready");
        Status statusProcessing = this.statusService.findStatusByStatus("Processing");

        LocalDateTime timeInPast = LocalDateTime.now().minusHours(2);

        Url url = this.urlService.fetchUrlByStoreNameAndStatusAndCheckedAtBefore(scraper.getName(), statusReady, timeInPast);
        if (url != null) {
            this.urlService.updateUrlLastCheckedAtAndStatus(url, url.getCheckedAt(), statusProcessing);
            scraper.setUrl(url);
            scraper.setCurrentPageUrl(url.getUrl());
        }
    }
}
