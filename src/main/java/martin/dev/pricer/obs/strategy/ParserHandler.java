package martin.dev.pricer.obs.strategy;

import martin.dev.pricer.scraper.model.ParsedItemDto;
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

    private ParsedItemDto parseItemModel(Element e, String urlFound){
        ParsedItemDto parsedItem = new ParsedItemDto();
        parsedItem.setTitle(parser.parseTitle(e));
        parsedItem.setPrice(parser.parsePrice(e));
        parsedItem.setImg(parser.parseImage(e));
        parsedItem.setUpc(parser.parseUpc(e));
        parsedItem.setUrl(parser.parseUrl(e));
        parsedItem.setUrlFound(urlFound);
        return parsedItem;
    }

    public List<ParsedItemDto> parseItemModels(Elements e, String urlFound) {
        return e.stream()
                .map(element -> parseItemModel(element, urlFound))
                .filter(ParsedItemDto::isValid)
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
