package martin.dev.pricer.scraper.batcing;

import martin.dev.pricer.data.model.Url;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.batch.item.ItemProcessor;

public class DocumentProcessor implements ItemProcessor<Document, String> {

    @Override
    public String process(Document document) throws Exception {
        return document.title();
    }
}
