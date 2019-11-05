package martin.dev.pricer.scraper.parser.inactive.johnlewis;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import martin.dev.pricer.scraper.parser.Parser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JohnLewisParser implements Parser {

    @Override
    public Elements parseListOfAdElements(Document pageContentInJsoupHtml) {
        return null;
    }

    @Override
    public int parseMaxPageNum(Document pageContentInJsoupHtml) {
        return 0;
    }

    @Override
    public String parseTitle(Element adInJsoupHtml) {
        return null;
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) {
        return null;
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) {
        return null;
    }

    @Override
    public String parseImage(Element adInJsoupHtml) {
        return null;
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) {
        return null;
    }

    @Override
    public ParsedItemDto fetchItemDtoFromHtml(Element adInJsoupHtml) {
        return null;
    }
}
