package martin.dev.pricer.scraper.parser.argos.parser;

import martin.dev.pricer.scraper.parser.PageParserImpl;
import org.jsoup.nodes.Document;

public class ArgosPage extends PageParserImpl {

    public ArgosPage(Document pageInJsoup) {
        super(pageInJsoup);
    }

    @Override
    public void parseListOfAdElements() {
        super.parseListOfAdElements();
    }

    @Override
    public void parseMaxPageNum() {
        super.parseMaxPageNum();
    }
}
