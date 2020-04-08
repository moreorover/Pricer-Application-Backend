package martin.dev.pricer.scraper;

import lombok.Getter;

@Getter
public abstract class Parser implements ParserI {

    private String NAME;
    private String PREFIX;
    private String BASE_URL;
    private int ADS_PER_PAGE;

    private String state;

    public Parser(String NAME, String PREFIX, String BASE_URL, int ads_per_page) {
        this.NAME = NAME;
        this.PREFIX = PREFIX;
        this.BASE_URL = BASE_URL;
        this.ADS_PER_PAGE = ads_per_page;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer parseIntegerFromString(String string) {
        String digits = string.replaceAll("[^\\d]", "");
        return Integer.parseInt(digits);
    }

    public Double parseDoubleFromString(String string) {
        String digits = string.replaceAll("[^\\d.]", "");
        return Double.parseDouble(digits);
    }

    public Integer calculateTotalPages(int adsCount) {
        return (adsCount + ADS_PER_PAGE - 1) / ADS_PER_PAGE;
    }
}
