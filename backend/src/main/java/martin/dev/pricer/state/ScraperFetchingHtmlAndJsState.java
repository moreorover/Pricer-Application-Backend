package martin.dev.pricer.state;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;

@Slf4j
public class ScraperFetchingHtmlAndJsState extends ScraperState {

    @Override
    public void fetchHtml(Scraper scraper) {
        while (true) {
            try {
                log.info("Attempting to fetch Html for:\n" + scraper.getCurrentPageUrl());
//                scraper.setPageHtmlDocument(Jsoup.connect(scraper.getCurrentPageUrl()).get());
                final WebClient webClient = new WebClient(BrowserVersion.CHROME);
                webClient.getOptions().setCssEnabled(false);
                webClient.getOptions().setJavaScriptEnabled(true);
                final HtmlPage page = webClient.getPage(scraper.getCurrentPageUrl());
                webClient.getCurrentWindow().setInnerHeight(60000);
                scraper.setPageHtmlDocument(Jsoup.parse(page.asText()));
                break;
            } catch (Exception e) {
                log.error("Catching IO Exception on: " + scraper.getCurrentPageUrl());
            }
        }
        scraper.changeState(State.ParsingHtml);
        scraper.validateResponse();
    }
}
