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
    }

    public void attach(ParserObserver parserObserver) {
        parserObservers.add(parserObserver);
    }

    public void notifyAllObservers() {
        parserObservers.forEach(parserObserver -> {
            if (parserObserver.getNAME().equals(this.store.getName())) {
                parserObserver.update();
            }
        });
    }
}
