package martin.dev.pricer.scraper.batcing;

import martin.dev.pricer.data.model.Url;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.batch.item.ItemProcessor;

public class UrlFilterProcessor implements ItemProcessor<Url, String> {

    @Override
    public String process(Url url) throws Exception {

        if (url.isReadyToScrape()) {
            Connection.Response document = Jsoup.connect(url.getUrl()).execute();
            System.out.println("URL " + url.getUrl());
            System.out.println("Status code " + document.statusCode());
            System.out.println("Content type " + document.contentType());
            return url.isReadyToScrape() ? document.parse().outerHtml() : null;
        }

        return null;
    }
}
