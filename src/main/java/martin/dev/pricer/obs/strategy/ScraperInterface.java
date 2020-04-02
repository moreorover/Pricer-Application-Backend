package martin.dev.pricer.obs.strategy;


public interface ScraperInterface {

    void scrape(int endPage);
    int getMaxPage();
}
