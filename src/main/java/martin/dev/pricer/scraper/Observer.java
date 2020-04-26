package martin.dev.pricer.scraper;

public abstract class Observer {
    protected ScraperSubject subject;

    public abstract void update();

    public abstract String getName();
}
