package martin.dev.pricer.scraper;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.service.ParserErrorService;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ParserHandler {

    private Parser parser;
    private ParserErrorService parserErrorService;

    public ParserHandler(Parser parser, ParserErrorService parserErrorService) {
        this.parser = parser;
        this.parserErrorService = parserErrorService;
    }

    private ParsedItemDto parseItemModel(Element element) {
        ParsedItemDto parsedItem = new ParsedItemDto();
        try {
            parsedItem.setTitle(parser.parseTitle(element));
            parsedItem.setPrice(parser.parsePrice(element));
            parsedItem.setImg(parser.parseImage(element));
            parsedItem.setUpc(parser.parseUpc(element));
            parsedItem.setUrl(parser.parseUrl(element));
            parsedItem.setUrlFound(parser.getCurrentPage());
        } catch (ParserException e) {
            parserErrorService.saveError(e);
        }
        return parsedItem;
    }

    public List<ParsedItemDto> parseItemModels(Elements e, String urlFound) {
        List<ParsedItemDto> parsedItemDtos = e.stream()
                .map(this::parseItemModel)
                .filter(ParsedItemDto::isValid)
                .collect(Collectors.toList());

        if (parsedItemDtos.size() == e.size()) {
            log.info("Successfully parsed " + parsedItemDtos.size() + " Ads");
        } else {
            log.warn("Parsed only " + parsedItemDtos.size() + " Ads. Out of total: " + e.size());
        }
        return parsedItemDtos;
    }

    public int parseMaxPageNum(Document d) {
        int maxPageNum = 0;
        try {
            maxPageNum = parser.parseMaxPageNum(d);
        } catch (ParserException e) {
            parserErrorService.saveError(e);
        }
        return maxPageNum;
    }

    public Elements parseItems(Document d) {
        Elements elements = new Elements();
        try {
            elements = parser.parseListOfAdElements(d);
        } catch (ParserException e) {
            parserErrorService.saveError(e);
        }
        return elements;
    }

    public String makeUrl(String url, int pageNum) {
        return parser.makeNextPageUrl(url, pageNum);
    }

    public String getParserName() {
        return parser.getNAME();
    }

    public void setCurrentUrl(String url) {
        this.parser.setCurrentPage(url);
    }
}
