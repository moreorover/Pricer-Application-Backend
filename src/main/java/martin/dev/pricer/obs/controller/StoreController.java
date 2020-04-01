package martin.dev.pricer.obs.controller;

import martin.dev.pricer.data.model.Url;
import martin.dev.pricer.scraper.client.HttpClient;
import org.jsoup.nodes.Document;

import java.util.List;

public class StoreController {

    private Url url;
    private List<ParsedItemModel> parsedItemModels;
    private Document pageContent;

    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }

    public List<ParsedItemModel> getParsedItemModels() {
        return parsedItemModels;
    }

    public void storeParsedItemModel(ParsedItemModel parsedItemModel) {
        this.parsedItemModels.add(parsedItemModel);
    }

    public Document sendGetRequest(String url) {
        this.pageContent = HttpClient.readContentInJsoupDocument(url);
        return this.pageContent;
    }

    public Document getPageContent() {
        return pageContent;
    }
}
