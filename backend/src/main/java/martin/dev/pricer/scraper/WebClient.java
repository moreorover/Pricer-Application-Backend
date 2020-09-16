package martin.dev.pricer.scraper;

public interface WebClient {

    void fetchSourceHtml(Scraper scraper);
    void close();
}
