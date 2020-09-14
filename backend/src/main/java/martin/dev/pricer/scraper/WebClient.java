package martin.dev.pricer.scraper;

import lombok.Data;

@Data
public abstract class WebClient {

    public abstract void fetchSourceHtml(Scraper scraper);
    public void closeWebDriver() {
    };
}
