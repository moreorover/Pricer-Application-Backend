package martin.dev.pricer.state;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@Slf4j
public class ScraperFetchingHtmlAndJsState extends ScraperState {

    private final WebClient webClient = new WebClient(BrowserVersion.CHROME);
//    ProxyConfig proxyConfig = new ProxyConfig("127.0.0.1", 8888);


    @Override
    public void fetchHtml(Scraper scraper) {
        while (true) {
            try {
                log.info("Attempting to fetch Html for:\n" + scraper.getCurrentPageUrl());
                HtmlPage page = webClient.getPage(scraper.getCurrentPageUrl());
//                webClient.getOptions().setProxyConfig(proxyConfig);
                webClient.getOptions().setJavaScriptEnabled(true);
                webClient.getOptions().setCssEnabled(true);
                webClient.waitForBackgroundJavaScript(3000);
                Document d = Jsoup.parse(page.asXml());
                scraper.setPageHtmlDocument(d);
                break;
            } catch (Exception e) {
                log.error("Catching IO Exception on: " + scraper.getCurrentPageUrl());
            }
        }
        scraper.changeState(State.ParsingHtml);
        scraper.validateResponse();
    }
}
