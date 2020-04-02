package martin.dev.pricer.scraper.parser;

import martin.dev.pricer.data.model.Store;
import martin.dev.pricer.data.model.Url;
import martin.dev.pricer.scraper.Observer;

import java.util.ArrayList;
import java.util.List;

public class ScraperSubject {

    private List<Observer> observers = new ArrayList<Observer>();

    private Store store;
    private Url url;

    public Store getStore() {
        return store;
    }

    public Url getUrl() {
        return url;
    }

    public void setStoreAndUrl(Store store, Url url) {
        this.store = store;
        this.url = url;
    }

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void notifyAllObservers() {
        observers.forEach(observer -> {
            if (observer.getName().equals(this.store.getName())) {
                observer.update();
            }
        });
    }
}
