package martin.dev.pricer.scraper.parser.ernestjones.parser;

import martin.dev.pricer.scraper.parser.PageParserImpl;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ErnestJonesPage extends PageParserImpl {

    public ErnestJonesPage(Document pageInJsoup) {
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
        Element lastPageElement = paginationButtons.get(7);
        String lastPageText = lastPageElement.text();
        setMaxPageNum(Integer.parseInt(lastPageText));
    }
}
