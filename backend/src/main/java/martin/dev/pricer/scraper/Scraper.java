package martin.dev.pricer.scraper;

import lombok.Data;
import martin.dev.pricer.data.model.Url;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class Scraper {

    private String name;

    private WebClient webClient;
    private DataReader dataReader;
    private Parser parser;
    private DataProcessor dataProcessor;
    private DataWriter dataWriter;
    private DataSender dataSender;

    private Url url;
    private Document pageHtmlDocument;
    private Elements ads;
    private List<ParsedItemDto> items = new ArrayList<>();
    private String currentPageUrl;
    private int currentPageNumber;

    public Scraper(String name, WebClient webClient, DataReader dataReader, Parser parser, DataProcessor dataProcessor, DataWriter dataWriter, DataSender dataSender) {
        this.name = name;
        this.webClient = webClient;
        this.dataReader = dataReader;
        this.parser = parser;
        this.dataProcessor = dataProcessor;
        this.dataWriter = dataWriter;
        this.dataSender = dataSender;
    }

    public void fetchUrl() {
        this.dataReader.fetchUrl(this);
        this.fetchHtml();
    }

    public void fetchHtml() {
        this.webClient.fetchSourceHtml(this);
        this.parseResponseToAds();
    }

    public void parseResponseToAds() {
        this.parser.parseListOfAdElements(this);
        this.parseAdsToItems();
    }

    public void validateAds() {
    }

    public void parseAdsToItems() {
        this.dataProcessor.processAdsToItems(this);
        this.processItems();
    }

    public void validateItems() {
    }

    public void processItems() {
        this.dataProcessor.processItems(this);
        this.nextPage();
    }

    public void writeItems() {
    }

    public void sendItems() {
    }

    public void nextPage() {
        if (this.parser.nextPageAvailable(this.pageHtmlDocument)) {
            this.nextPageUrl();
            this.fetchHtml();
        } else {
            this.webClient.closeWebDriver();
            this.dataWriter.write(this);
            this.dataSender.send();
        }
    }

    public abstract void nextPageUrl();
}
