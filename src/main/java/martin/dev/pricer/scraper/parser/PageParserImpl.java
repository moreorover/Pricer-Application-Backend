package martin.dev.pricer.scraper.parser;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public abstract class PageParserImpl implements PageParser {

    private Document pageInJsoup;
    private Elements adElements;
    private int maxPageNum;

    public PageParserImpl(Document pageInJsoup) {
        this.pageInJsoup = pageInJsoup;
    }

    @Override
    public int getMaxPageNum() {
        return maxPageNum;
    }

    public void setMaxPageNum(int maxPageNum) {
        this.maxPageNum = maxPageNum;
    }

    @Override
    public Elements getAdElements() {
        return adElements;
    }

    public void setAdElements(Elements adElements) {
        this.adElements = adElements;
    }

    public Document getPageInJsoup() {
        return pageInJsoup;
    }

    @Override
    public void parseListOfAdElements() {
    }

    @Override
    public void parseMaxPageNum() {

    }
}
