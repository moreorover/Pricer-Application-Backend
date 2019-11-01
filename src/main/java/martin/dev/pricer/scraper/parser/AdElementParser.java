package martin.dev.pricer.scraper.parser;

import martin.dev.pricer.scraper.model.ParsedItemDto;
import org.jsoup.nodes.Element;

public interface AdElementParser {

    Element getAdInHtml();

    String parseTitle();
    String parseUpc();
    Double parsePrice();
    String parseImage();
    String parseUrl();

    ParsedItemDto parseAll();

}
