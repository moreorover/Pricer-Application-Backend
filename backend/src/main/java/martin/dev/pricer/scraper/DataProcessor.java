package martin.dev.pricer.scraper;

public abstract class DataProcessor {

    public abstract void processAdsToItems(Scraper scraper);

    public abstract void processItems(Scraper scraper);

}
