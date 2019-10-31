package martin.dev.pricer.scraper.parser.hsamuel.parser;

import martin.dev.pricer.scraper.parser.PageParserImpl;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HSamuelPage extends PageParserImpl {

    public HSamuelPage(Document pageInJsoup) {
        super(pageInJsoup);
    }

    @Override
    public void parseListOfAdElements() {
        setAdElements(getPageInJsoup().select("div.product-tile.js-product-item"));
    }

    @Override
    public void parseMaxPageNum() {
        Element paginationBlockElement = getPageInJsoup().selectFirst("ol[class*=pageNumbers]");
        Elements paginationButtons = paginationBlockElement.select("li");
        Element lastPageElement = paginationButtons.get(5);
        String lastPageText = lastPageElement.text();
        setMaxPageNum(Integer.parseInt(lastPageText));
    }
}
