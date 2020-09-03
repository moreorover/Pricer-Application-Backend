package martin.dev.pricer.state;

import martin.dev.pricer.data.model.Status;
import martin.dev.pricer.data.model.Url;
import martin.dev.pricer.data.service.StatusService;
import martin.dev.pricer.data.service.UrlService;

import java.time.LocalDateTime;

public class ScraperReadingState extends ScraperState {

    private StatusService statusService;
    private UrlService urlService;

    public ScraperReadingState(StatusService statusService, UrlService urlService) {
        this.statusService = statusService;
        this.urlService = urlService;
    }

    @Override
    public void fetchUrl(Scraper scraper) {
        Status statusReady = this.statusService.findStatusByStatus("Ready");
//        Status statusProcessing = this.statusService.findStatusByStatus("Processing");
//        Status statusDisabled = this.statusService.findStatusByStatus("Disabled");

        LocalDateTime timeInPast = LocalDateTime.now().minusHours(2);

        Url url = this.urlService.fetchUrlByStatusAndCheckedAtBefore(statusReady, timeInPast);
        scraper.setUrl(url);
        scraper.setCurrentPageUrl(url.getUrl());
        scraper.changeState(State.FetchingHtml);
        scraper.fetchHtml();
    }
}
