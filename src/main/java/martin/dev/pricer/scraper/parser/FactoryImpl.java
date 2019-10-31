package martin.dev.pricer.scraper.parser;

import martin.dev.pricer.scraper.model.ParsedItemDto;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;

public abstract class FactoryImpl<T extends PageParser> implements Factory {

    private T page;

    public T getPage() {
        return page;
    }

    public void setPage(T page) {
        this.page = page;
    }

    @Override
    public int getMaxPageNumber() {
        return 0;
    }

    @Override
    public Elements getAds() {
        return null;
    }

    @Override
    public List<ParsedItemDto> getParsedAds() {
        return null;
    }
}
