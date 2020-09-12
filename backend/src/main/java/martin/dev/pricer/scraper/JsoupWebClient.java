package martin.dev.pricer.scraper;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

@EqualsAndHashCode(callSuper = true)
@Slf4j
@Data
public class JsoupWebClient extends WebClient<Document> {

    @Override
    public void fetchSourceHtml(String url) {
        log.info("Attempting to fetch Html for:\n" + url);
        Document d = null;
        try {
            d = Jsoup
                    .connect(url)
    //                        .proxy("127.0.0.1", 8888)
                    .get();
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        this.setPageSource(d);
    }
}
