package martin.dev.pricer.scraper;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;

import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
public class ScraperFetchingHtmlSeleniumState extends ScraperState {

    private RemoteWebDriver webDriver;

    @Value("${price.remote.browser}")
    private String remoteBrowserUrl;

    private void getWebDriver() {
        log.info("Trying to create new WebDriver session!");
        URL remoteAddress = null;
        try {
            remoteAddress = new URL(remoteBrowserUrl);
        } catch (MalformedURLException e) {
            log.info(e.getMessage());
        }
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--whitelisted-ips");
        chromeOptions.setHeadless(true);

        this.webDriver = new RemoteWebDriver(remoteAddress, chromeOptions);
    }

    @Override
    public void fetchHtml(Scraper scraper) {
        if (this.webDriver == null || this.webDriver.getSessionId() == null) {
            this.getWebDriver();
        }

        while (true) {
            try {
                log.info("Attempting to fetch Html for:\n" + scraper.getCurrentPageUrl());
                this.webDriver.get(scraper.getCurrentPageUrl());
                WebDriverWait wait = new WebDriverWait(this.webDriver, 10);
                wait.until((ExpectedCondition<Boolean>) wd ->
                        ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
                String source = this.webDriver.getPageSource();
//                this.webDriver.close();
                this.webDriver.quit();
                Document d = Jsoup.parse(source);
                scraper.setPageHtmlDocument(d);
                break;
            } catch (NoSuchSessionException e) {
                log.info("Catching No Such Session Exception.");
                this.getWebDriver();
                log.info(e.getMessage());
            }
        }
        scraper.changeState(State.ParsingHtml);
        scraper.validateResponse();
    }
}
