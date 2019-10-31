package martin.dev.pricer.scraper.parser;

import org.jsoup.select.Elements;

public interface PageParser {
    int getMaxPageNum();

    Elements getAdElements();

    void parseListOfAdElements();

    void parseMaxPageNum();
}
