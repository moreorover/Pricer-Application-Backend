package martin.dev.pricer.scraper.parser;

import martin.dev.pricer.scraper.model.ParsedItemDto;
import org.jsoup.nodes.Element;

public abstract class AdElementParserImpl implements AdElementParser {

    private Element adInHtml;

    public AdElementParserImpl(Element adInHtml) {
        this.adInHtml = adInHtml;
    }

    @Override
    public final Element getAdInHtml() {
        return adInHtml;
    }

    @Override
    public String parseTitle() {
        return null;
    }

    @Override
    public String parseUpc() {
        return null;
    }

    @Override
    public Double parsePrice() {
        return null;
    }

    @Override
    public String parseImage() {
        return null;
    }

    @Override
    public String parseUrl() {
        return null;
    }

    @Override
    public ParsedItemDto parseAll() {
        return null;
    }
}
