package martin.dev.pricer.scraper;

public enum State {
    ReadingDatabase, FetchingHtml, ParsingHtml, ProcessingAds, SendingAds
}
