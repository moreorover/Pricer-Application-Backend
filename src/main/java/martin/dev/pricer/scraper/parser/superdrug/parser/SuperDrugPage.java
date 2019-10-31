package martin.dev.pricer.scraper.parser.superdrug.parser;

import martin.dev.pricer.scraper.parser.PageParserImpl;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class SuperDrugPage extends PageParserImpl {

    public SuperDrugPage(Document pageInJsoup) {
        super(pageInJsoup);
    }

    @Override
    public void parseListOfAdElements() {
        setAdElements(getPageInJsoup().select("div[class=item__content]"));
    }

    @Override
    public void parseMaxPageNum() {
        Elements paginationElements = getPageInJsoup().select("ul[class=pagination__list]");
        paginationElements = paginationElements.select("li");
        int countOfPaginationElements = paginationElements.size();
        if (countOfPaginationElements == 0) {
            setMaxPageNum(0);
        } else {
            String elementText = paginationElements.get(countOfPaginationElements - 2).text();
            setMaxPageNum(Integer.parseInt(elementText));
        }
    }
}
