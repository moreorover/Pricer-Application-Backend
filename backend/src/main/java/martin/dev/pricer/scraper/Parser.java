package martin.dev.pricer.scraper;

import martin.dev.pricer.scraper.model.ParsedItemDto;
import org.jsoup.nodes.Element;

import java.util.List;

public interface Parser {

    String makeNextPageUrl(int pageNum);

    void parseListOfAdElements();

    void parseMaxPageNum();

    String parseTitle(Element adInJsoupHtml);

    String parseUpc(Element adInJsoupHtml);

    Double parsePrice(Element adInJsoupHtml);

    String parseImage(Element adInJsoupHtml);

    String parseUrl(Element adInJsoupHtml);

    Integer parseIntegerFromString(String string) throws ParserException;

    Double parseDoubleFromString(String string) throws ParserException;

    Integer calculateTotalPages(int adsCount);

    ParsedItemDto parseItemModel(Element element);

    List<ParsedItemDto> parseItemModels();
}
