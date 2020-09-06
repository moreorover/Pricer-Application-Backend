package martin.dev.pricer.state;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value;

import java.net.URL;

@Slf4j
public class ScraperFetchingHtmlSeleniumState extends ScraperState {


    @Value("${price.remote.browser}")
    private String remoteBrowserUrl;


    @Override
    public void fetchHtml(Scraper scraper) {
        while (true) {
            try {
                log.info("Attempting to fetch Html for:\n" + scraper.getCurrentPageUrl());
                URL remoteAddress = new URL(remoteBrowserUrl);
                Capabilities desiredCapabilities = DesiredCapabilities.chrome();

                WebDriver webDriver = new RemoteWebDriver(remoteAddress, desiredCapabilities);
                webDriver.get(scraper.getCurrentPageUrl());
//                webClient.getOptions().setProxyConfig(proxyConfig);
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
