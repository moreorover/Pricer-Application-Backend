package martin.dev.pricer.scraper;

import martin.dev.pricer.flyway.model.Url;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

public interface ParserHandler {

    ParsedItemDto parseItemModel(Element element);

    List<ParsedItemDto> parseItemModels(Elements e, String url);

    List<ParsedItemDto> parseItemModels(Elements e, Url url);

    int parseMaxPageNum(Document d);

    Elements parseItems(Document d);

    void makeUrl(String url, int pageNum);

    String getParserName();

    void setCurrentUrl(String url);

    String getCurrentUrl();
}
