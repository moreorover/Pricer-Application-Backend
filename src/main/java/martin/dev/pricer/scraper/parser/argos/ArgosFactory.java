package martin.dev.pricer.scraper.parser.argos;

import martin.dev.pricer.scraper.model.ParsedItemDto;
import martin.dev.pricer.scraper.parser.FactoryImpl;
import martin.dev.pricer.scraper.parser.argos.parser.ArgosPage;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;

public class ArgosFactory extends FactoryImpl<ArgosPage> {

    public ArgosFactory(Document pageContentInJsoup) {
        setPage(new ArgosPage(pageContentInJsoup));
    }

    @Override
    public int getMaxPageNumber() {
        return super.getMaxPageNumber();
    }

    @Override
    public Elements getAds() {
        return super.getAds();
    }

    @Override
    public List<ParsedItemDto> getParsedAds() {
        return super.getParsedAds();
    }
}
