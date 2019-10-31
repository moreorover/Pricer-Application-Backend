package martin.dev.pricer.scraper.parser.ernestjones;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import martin.dev.pricer.scraper.parser.FactoryImpl;
import martin.dev.pricer.scraper.parser.ernestjones.parser.ErnestJonesAdElement;
import martin.dev.pricer.scraper.parser.ernestjones.parser.ErnestJonesPage;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ErnestJonesFactory extends FactoryImpl<ErnestJonesPage> {

    public ErnestJonesFactory(Document pageContentInJsoup) {
        setPage(new ErnestJonesPage(pageContentInJsoup));
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
            ErnestJonesAdElement ErnestJonesAdElement = new ErnestJonesAdElement(adInHtml);
            itemDtoList.add(ErnestJonesAdElement.parseAll());
        });

        return itemDtoList;
    }
}
