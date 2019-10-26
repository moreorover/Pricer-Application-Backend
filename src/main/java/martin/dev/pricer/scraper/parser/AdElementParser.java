package martin.dev.pricer.scraper.parser;

import java.util.HashMap;

public interface AdElementParser {

    String parseTitle();
    String parseUpc();
    String parsePrice();
    String parseImage();
    String parseUrl();

    HashMap<String, String> parseAll();

}
