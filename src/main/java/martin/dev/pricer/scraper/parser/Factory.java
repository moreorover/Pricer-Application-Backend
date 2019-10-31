package martin.dev.pricer.scraper.parser;

import martin.dev.pricer.scraper.model.ParsedItemDto;
import org.jsoup.select.Elements;

import java.util.List;

public interface Factory {

    int getMaxPageNumber();

    Elements getAds();

    List<ParsedItemDto> getParsedAds();
}
