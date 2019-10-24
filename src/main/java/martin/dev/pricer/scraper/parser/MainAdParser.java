package martin.dev.pricer.scraper.parser;

public interface MainAdParser {

    String parseTitle();
    String parseUpc();
    Double parsePrice();
    String parseImage();
    String parseUrl();

}
