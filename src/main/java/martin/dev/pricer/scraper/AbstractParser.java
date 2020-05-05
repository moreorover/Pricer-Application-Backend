package martin.dev.pricer.scraper;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.flyway.model.Url;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Getter
@Setter
public abstract class AbstractParser implements Parser {

    private ParserValidator parserValidator;

    // TODO move the creation of parser object into beans wiring
    private final String NAME;
    private final String PREFIX;
    private final String BASE_URL;
    private final int ADS_PER_PAGE;
    private final int START_PAGE_NUMBER;

    private int MAX_PAGE_NUMBER;

    private Url urlObject;

    private String currentPageUrl;
    private Document document;
    private Elements elements;

    public AbstractParser(ParserValidator parserValidator, String NAME, String PREFIX, String BASE_URL, int ADS_PER_PAGE, int START_PAGE_NUMBER) {
        this.parserValidator = parserValidator;
        this.NAME = NAME;
        this.PREFIX = PREFIX;
        this.BASE_URL = BASE_URL;
        this.ADS_PER_PAGE = ADS_PER_PAGE;
        this.START_PAGE_NUMBER = START_PAGE_NUMBER;
    }

    public void setMAX_PAGE_NUMBER(int MAX_PAGE_NUMBER) {
        if (this.START_PAGE_NUMBER == 0) {
            this.MAX_PAGE_NUMBER = MAX_PAGE_NUMBER - 1;
        } else {
            this.MAX_PAGE_NUMBER = MAX_PAGE_NUMBER;
        }
    }

    @Override
    public Integer parseIntegerFromString(String string) throws ParserException {
        String digits = string.replaceAll("[^\\d]", "");
        this.parserValidator.validate(digits, 1, "parseIntegerFromString");
        return Integer.parseInt(digits);
    }

    @Override
    public Double parseDoubleFromString(String string) throws ParserException {
        String digits = string.replaceAll("[^\\d.]", "");
        this.parserValidator.validate(digits, 1, "parseDoubleFromString");
        return Double.parseDouble(digits);
    }

    @Override
    public Integer calculateTotalPages(int adsCount) {
        return (adsCount + ADS_PER_PAGE - 1) / ADS_PER_PAGE;
    }

    @Override
    public ParsedItemDto parseItemModel(Element element) {
        ParsedItemDto parsedItem = new ParsedItemDto();
        parsedItem.setTitle(parseTitle(element));
        parsedItem.setPrice(parsePrice(element));
        parsedItem.setImg(parseImage(element));
        parsedItem.setUpc(parseUpc(element));
        parsedItem.setUrl(parseUrl(element));
        parsedItem.setUrlFound(getCurrentPageUrl());
        return parsedItem;
    }

    @Override
    public List<ParsedItemDto> parseItemModels() {
        List<ParsedItemDto> parsedItemDtos = getElements().stream()
                .map(this::parseItemModel)
                .filter(ParsedItemDto::isValid)
                .collect(Collectors.toList());

        parsedItemDtos.forEach(parsedItemDto -> parsedItemDto.setUrlObject(getUrlObject()));
        parsedItemDtos.forEach(parsedItemDto -> parsedItemDto.setFoundTime(LocalDateTime.now()));

        if (parsedItemDtos.size() == getElements().size()) {
            log.info("Successfully parsed " + parsedItemDtos.size() + " Ads");
        } else {
            log.warn("Parsed only " + parsedItemDtos.size() + " Ads. Out of total: " + getElements().size());
        }
        return parsedItemDtos;
    }

}
