package martin.dev.pricer.scraper.parser.superdrug.parser;

import martin.dev.pricer.scraper.parser.PageParser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SuperDrugPage implements PageParser {

    private Document pageInJsoup;
    private Elements adElements;
    private int maxPageNum;
    private String nextPageUrl;

    public SuperDrugPage(Document pageInJsoup) {
        this.pageInJsoup = pageInJsoup;
    }

    @Override
    public int getMaxPageNum() {
        return maxPageNum;
    }

    @Override
    public Elements getAdElements() {
        return adElements;
    }

    @Override
    public void parseListOfAdElements() {
        adElements = pageInJsoup.select("div[class=item__content]");
    }

    @Override
    public void parseMaxPageNum() {
        Elements paginationElements = pageInJsoup.select("ul[class=pagination__list]");
        paginationElements = paginationElements.select("li");
        int countOfPaginationElements = paginationElements.size();
        if (countOfPaginationElements == 0){
            maxPageNum = 0;
        } else {
            String elementText = paginationElements.get(countOfPaginationElements - 2).text();
            maxPageNum = Integer.parseInt(elementText);
        }
    }
}
