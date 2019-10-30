package martin.dev.pricer.scraper.parser;

import martin.dev.pricer.scraper.model.ParsedItemDto;

public interface AdElementParser {

    String parseTitle();
    String parseUpc();
    Double parsePrice();
    String parseImage();
    String parseUrl();

    ParsedItemDto parseAll();

}
