package martin.dev.pricer.obs.strategy;

import martin.dev.pricer.obs.controller.ParsedItemModel;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.stream.Collectors;

public class ParserHandler {

    private Parser parser;

    public ParserHandler(Parser parser) {
        this.parser = parser;
    }

    private ParsedItemModel parseItemModel(Element e, String urlFound){
        ParsedItemModel parsedItem = new ParsedItemModel();
        parsedItem.setTitle(parser.parseTitle(e));
        parsedItem.setPrice(parser.parsePrice(e));
        parsedItem.setImg(parser.parseImage(e));
        parsedItem.setUpc(parser.parseUpc(e));
        parsedItem.setUrl(parser.parseUrl(e));
        parsedItem.setUrlFound(urlFound);
        return parsedItem;
    }

    public List<ParsedItemModel> parseItemModels(Elements e, String urlFound) {
        return e.stream()
                .map(element -> parseItemModel(element, urlFound))
                .filter(ParsedItemModel::isValid)
                .collect(Collectors.toList());
    }

    public int parseMaxPageNum(Document d) {
        return parser.parseMaxPageNum(d);
    }

    public Elements parseItems(Document d) {
        return parser.parseListOfAdElements(d);
    }

    public String makeUrl(String url, int pageNum) {
        return parser.makeNextPageUrl(url, pageNum);
    }

}
