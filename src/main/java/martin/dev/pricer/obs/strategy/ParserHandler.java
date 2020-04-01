package martin.dev.pricer.obs.strategy;

import martin.dev.pricer.obs.controller.ParsedItemModel;
import org.jsoup.nodes.Element;

public class ParserHandler {

    private Parser parser;

    public ParserHandler(Parser parser) {
        this.parser = parser;
    }

    public ParsedItemModel parseItemModel(Element e, String urlFound){
        ParsedItemModel parsedItem = new ParsedItemModel();
        parsedItem.setTitle(parser.parseTitle(e));
        parsedItem.setPrice(parser.parsePrice(e));
        parsedItem.setImg(parser.parseImage(e));
        parsedItem.setUpc(parser.parseUpc(e));
        parsedItem.setUrl(parser.parseUrl(e));
        parsedItem.setUrlFound(urlFound);
        return parsedItem;
    }
}
