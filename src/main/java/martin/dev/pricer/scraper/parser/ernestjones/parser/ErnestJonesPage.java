package martin.dev.pricer.scraper.parser.ernestjones.parser;

import martin.dev.pricer.scraper.parser.PageParser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ErnestJonesPage implements PageParser {

    private Document pageInJsoup;
    private Elements adElements;
    private int maxPageNum;

    public ErnestJonesPage(Document pageInJsoup) {
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
        adElements = pageInJsoup.select("div.product-tile.js-product-item");
    }

    @Override
    public void parseMaxPageNum() {
        Element paginationBlockElement = pageInJsoup.selectFirst("ol[class*=pageNumbers]");
        Elements paginationButtons = paginationBlockElement.select("li");
        Element lastPageElement = paginationButtons.get(7);
        String lastPageText = lastPageElement.text();
        maxPageNum = Integer.parseInt(lastPageText);
    }
}
