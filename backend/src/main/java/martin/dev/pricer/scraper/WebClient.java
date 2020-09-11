package martin.dev.pricer.scraper;

import lombok.Data;

@Data
public abstract class WebClient<T> {

    private T pageSource;

    public abstract void fetchSourceHtml(String url);
}
