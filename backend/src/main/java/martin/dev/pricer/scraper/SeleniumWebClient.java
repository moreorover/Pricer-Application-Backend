package martin.dev.pricer.scraper;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;

import java.net.MalformedURLException;
import java.net.URL;

@Data
@Slf4j
public class SeleniumWebClient implements WebClient {

    private RemoteWebDriver webDriver;
    private boolean headless;

    @Value("${price.remote.browser}")
    private String remoteBrowserUrl;

    public SeleniumWebClient(boolean headless) {
        this.headless = headless;
    }

    private void startWebDriver() {
        log.info("Trying to create new WebDriver session!");
        URL remoteAddress = null;
        try {
            remoteAddress = new URL(remoteBrowserUrl);
        } catch (MalformedURLException e) {
            log.info(e.getMessage());
        }
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(this.headless);

        this.webDriver = new RemoteWebDriver(remoteAddress, chromeOptions);
    }

    public void fetchSourceHtml(Scraper scraper) {
        if (this.webDriver == null || this.webDriver.getSessionId() == null) {
            this.startWebDriver();
        }

        log.info("Attempting to fetch Html for:\n" + scraper.getCurrentPageUrl());
        this.webDriver.get(scraper.getCurrentPageUrl());
        WebDriverWait wait = new WebDriverWait(this.webDriver, 10);
        wait.until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
        String source = this.webDriver.getPageSource();
        Document document = Jsoup.parse(source);
        document.setBaseUri(scraper.getUrl().getStore().getUrl());
        scraper.setPageHtmlDocument(document);
    }

    public void close() {
        if (this.webDriver != null) {
            log.info("Closing a Web Driver session. " + this.webDriver.getSessionId().toString());
            this.webDriver.quit();
        }
    }
}
