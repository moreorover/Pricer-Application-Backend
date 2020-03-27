package martin.dev.pricer.obs;

import martin.dev.pricer.data.model.Store;
import martin.dev.pricer.data.model.Url;

import java.util.ArrayList;
import java.util.List;

public class Subject {

    private List<ParserObserver> parserObservers = new ArrayList<ParserObserver>();

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
        notifyAllObservers();
    }

    public void attach(ParserObserver parserObserver) {
        parserObservers.add(parserObserver);
    }

    public void notifyAllObservers() {
        for (ParserObserver parserObserver : parserObservers) {
            if (parserObserver.NAME.equals(this.store.getName())) {
                parserObserver.update();
            }
        }
    }
}
