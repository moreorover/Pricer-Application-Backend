package martin.dev.pricer.scraper.parser.argos;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import martin.dev.pricer.scraper.parser.FactoryImpl;
import martin.dev.pricer.scraper.parser.argos.parser.ArgosAdElement;
import martin.dev.pricer.scraper.parser.argos.parser.ArgosPage;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ArgosFactory extends FactoryImpl<ArgosPage> {

    public ArgosFactory(Document pageContentInJsoup) {
        setPage(new ArgosPage(pageContentInJsoup));
    }

    @Override
    public int getMaxPageNumber() {
        getPage().parseMaxPageNum();
        return getPage().getMaxPageNum();
    }

    @Override
    public Elements getAds() {
        getPage().parseListOfAdElements();
        return getPage().getAdElements();
    }

    @Override
    public List<ParsedItemDto> getParsedAds() {
        List<ParsedItemDto> itemDtoList = new ArrayList<>();

        getAds().forEach(adInHtml -> {
            log.info(adInHtml.outerHtml());
            ArgosAdElement hSamuelAdElement = new ArgosAdElement(adInHtml);
            itemDtoList.add(hSamuelAdElement.parseAll());
        });

        return itemDtoList;
    }
}
