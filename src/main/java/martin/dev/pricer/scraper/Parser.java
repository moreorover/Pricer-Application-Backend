package martin.dev.pricer.scraper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Parser implements ParserI {

    private String NAME;
    private String PREFIX;
    private String BASE_URL;
    private int ADS_PER_PAGE;

    private String state;
    private String currentPage;

    public Parser(String NAME, String PREFIX, String BASE_URL, int ads_per_page) {
        this.NAME = NAME;
        this.PREFIX = PREFIX;
        this.BASE_URL = BASE_URL;
        this.ADS_PER_PAGE = ads_per_page;
    }

    public Integer parseIntegerFromString(String string) throws ParserException {
        String digits = string.replaceAll("[^\\d]", "");
        ParserValidator.validateStringIsNotEmpty(digits, this);
        return Integer.parseInt(digits);
    }

    public Double parseDoubleFromString(String string) throws ParserException {
        String digits = string.replaceAll("[^\\d.]", "");
        ParserValidator.validateStringIsNotEmpty(digits, this);
        return Double.parseDouble(digits);
    }

    public Integer calculateTotalPages(int adsCount) {
        return (adsCount + ADS_PER_PAGE - 1) / ADS_PER_PAGE;
    }
}
