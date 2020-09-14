package martin.dev.pricer.scraper.scrapers;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.*;

@Slf4j
public class GoldSmithsScraper extends Scraper {

    public GoldSmithsScraper(WebClient webClient, DataReader dataReader, Parser parser, DataProcessor dataProcessor, DataWriter dataWriter, DataSender dataSender) {
        super("Gold Smiths", webClient, dataReader, parser, dataProcessor, dataWriter, dataSender);
    }

    @Override
    public void nextPageUrl() {
        // https://www.goldsmiths.co.uk/c/Watches/Ladies-Watches/filter/Page_1/Psize_96/Show_Page/
        String[] x = this.getCurrentPageUrl().split("Page_");
        String[] y = x[1].split("/");
        int pageNumber = ScraperTools.parseIntegerFromString(y[0]) + 1;
        this.setCurrentPageNumber(pageNumber);
        this.setCurrentPageUrl(x[0] + "Page_" + pageNumber + "/Psize_96/Show_Page/");
    }
}
