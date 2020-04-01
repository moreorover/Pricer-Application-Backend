package martin.dev.pricer.obs;

import martin.dev.pricer.obs.strategy.ScraperSubject;

public abstract class Observer {
    protected ScraperSubject subject;

    public abstract void update();

    public abstract String getName();
}
