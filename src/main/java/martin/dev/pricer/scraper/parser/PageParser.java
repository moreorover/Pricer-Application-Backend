package martin.dev.pricer.scraper.parser;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public interface PageParser {

    int getMaxPageNum();

    void setMaxPageNum(int maxPageNum);

    Elements getAdElements();

    void setAdElements(Elements adElements);

    Document getPageInJsoup();

    void parseListOfAdElements();

    void parseMaxPageNum();
}
