package martin.dev.pricer.scraper.parser.superdrug;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import martin.dev.pricer.scraper.parser.FactoryImpl;
import martin.dev.pricer.scraper.parser.superdrug.parser.SuperDrugAdElement;
import martin.dev.pricer.scraper.parser.superdrug.parser.SuperDrugPage;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SuperDrugFactory extends FactoryImpl<SuperDrugPage> {

    public SuperDrugFactory(Document pageContentInJsoup) {
        setPage(new SuperDrugPage(pageContentInJsoup));
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
            SuperDrugAdElement superDrugAdElement = new SuperDrugAdElement(adInHtml);
            itemDtoList.add(superDrugAdElement.parseAll());
        });

        return itemDtoList;
    }
}
