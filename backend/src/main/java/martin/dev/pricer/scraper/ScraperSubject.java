package martin.dev.pricer.scraper;

import martin.dev.pricer.data.model.Url;

import java.util.ArrayList;
import java.util.List;

public class ScraperSubject {
    private List<Observer> observers = new ArrayList<Observer>();

    private Url url;

    public Url getUrl() {
        return url;
    }

    public void setStoreAndUrl(Url url) {
        this.url = url;
    }

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void notifyAllObservers() {
        observers.forEach(observer -> {
            if (observer.getName().equals(this.getUrl().getStore().getName())) {
                observer.update();
            }
        });
    }
}
