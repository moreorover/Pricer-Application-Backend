package martin.dev.pricer.scraper;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

@Slf4j
public class ScraperFetchingHtmlSeleniumState extends ScraperState {

    private final WebDriver webDriver;

    public ScraperFetchingHtmlSeleniumState(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Override
    public void fetchHtml(Scraper scraper) {
        while (true) {
            try {
                log.info("Attempting to fetch Html for:\n" + scraper.getCurrentPageUrl());
                this.webDriver.get(scraper.getCurrentPageUrl());
//                webClient.getOptions().setProxyConfig(proxyConfig);
//                WebDriverWait wait = new WebDriverWait(this.webDriver, 10);
//                wait.until((ExpectedCondition<Boolean>) wd ->
//                        ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
                String source = webDriver.getPageSource();
                Document d = Jsoup.parse(source);
                scraper.setPageHtmlDocument(d);
                break;
            } catch (Exception e) {
                log.info(e.getMessage());
                log.error("Catching IO Exception on: " + scraper.getCurrentPageUrl());
            }
        }
        scraper.changeState(State.ParsingHtml);
        scraper.validateResponse();
    }
}
