package martin.dev.pricer.scraper;

import lombok.Data;
import lombok.EqualsAndHashCode;
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

@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class SeleniumWebClient extends WebClient<Document> {

    private RemoteWebDriver webDriver;

    @Value("${price.remote.browser}")
    private String remoteBrowserUrl;

    public void startWebDriver(boolean headless) {
        log.info("Trying to create new WebDriver session!");
        URL remoteAddress = null;
        try {
            remoteAddress = new URL(remoteBrowserUrl);
        } catch (MalformedURLException e) {
            log.info(e.getMessage());
        }
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(headless);

        this.webDriver = new RemoteWebDriver(remoteAddress, chromeOptions);
    }

    @Override
    public void fetchSourceHtml(String pageUrl) {
        if (this.webDriver == null || this.webDriver.getSessionId() == null) {
            log.error("Start the web browser first.");
        }

        log.info("Attempting to fetch Html for:\n" + pageUrl);
        this.webDriver.get(pageUrl);
        WebDriverWait wait = new WebDriverWait(this.webDriver, 10);
        wait.until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
        String source = this.webDriver.getPageSource();
        this.setPageSource(Jsoup.parse(source));

    }

    public void closeWebDriver() {
        this.getWebDriver().quit();
    }
}
