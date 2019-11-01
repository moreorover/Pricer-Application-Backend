package martin.dev.pricer.scraper.parser.argos.parser;

import martin.dev.pricer.scraper.parser.PageParserImpl;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ArgosPage extends PageParserImpl {

    public ArgosPage(Document pageInJsoup) {
        super(pageInJsoup);
    }

    @Override
    public void parseListOfAdElements() {
        setAdElements(getPageInJsoup().select("div[class^=ProductCardstyles__Wrapper-]"));
    }

    @Override
    public void parseMaxPageNum() {
        Element searchResultsCount = getPageInJsoup().selectFirst("div[class*=search-results-count]");
        String countString = searchResultsCount.attr("data-search-results");
        int count = Integer.parseInt(countString);
//        int maxPages = (int) Math.ceil(count / 30.0);
        int maxPage = (count + 30 - 1) / 30;
        setMaxPageNum(maxPage);
    }
}
