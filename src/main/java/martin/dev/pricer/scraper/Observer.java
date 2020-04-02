package martin.dev.pricer.scraper;

import martin.dev.pricer.scraper.parser.ScraperSubject;

public abstract class Observer {
    protected ScraperSubject subject;

    public abstract void update();

    public abstract String getName();
}
