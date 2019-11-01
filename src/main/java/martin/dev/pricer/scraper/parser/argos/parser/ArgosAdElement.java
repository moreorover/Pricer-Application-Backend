package martin.dev.pricer.scraper.parser.argos.parser;

import martin.dev.pricer.scraper.model.ParsedItemDto;
import martin.dev.pricer.scraper.parser.AdElementParserImpl;
import org.jsoup.nodes.Element;

public class ArgosAdElement extends AdElementParserImpl {

    public ArgosAdElement(Element adInHtml) {
        super(adInHtml);
    }

    @Override
    public String parseTitle() {
        return super.parseTitle();
    }

    @Override
    public String parseUpc() {
        return super.parseUpc();
    }

    @Override
    public Double parsePrice() {
        return super.parsePrice();
    }

    @Override
    public String parseImage() {
        return super.parseImage();
    }

    @Override
    public String parseUrl() {
        return super.parseUrl();
    }

    @Override
    public ParsedItemDto parseAll() {
        return super.parseAll();
    }
}
