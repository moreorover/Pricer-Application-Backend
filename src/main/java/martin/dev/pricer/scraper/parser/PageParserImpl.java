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
    public final int getMaxPageNum() {
        return maxPageNum;
    }

    @Override
    public final void setMaxPageNum(int maxPageNum) {
        this.maxPageNum = maxPageNum;
    }

    @Override
    public final Elements getAdElements() {
        return adElements;
    }

    @Override
    public final void setAdElements(Elements adElements) {
        this.adElements = adElements;
    }

    @Override
    public final Document getPageInJsoup() {
        return pageInJsoup;
    }

    @Override
    public void parseListOfAdElements() {
    }

    @Override
    public void parseMaxPageNum() {

    }
}
