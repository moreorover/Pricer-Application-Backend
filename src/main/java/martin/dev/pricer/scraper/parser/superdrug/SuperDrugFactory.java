package martin.dev.pricer.scraper.parser.superdrug;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import martin.dev.pricer.scraper.parser.Factory;
import martin.dev.pricer.scraper.parser.superdrug.parser.SuperDrugAdElement;
import martin.dev.pricer.scraper.parser.superdrug.parser.SuperDrugPage;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SuperDrugFactory implements Factory {

    private SuperDrugPage superDrugPage;

    SuperDrugFactory(Document pageContentInJsoup) {
        this.superDrugPage = new SuperDrugPage(pageContentInJsoup);
    }

    @Override
    public int getMaxPageNumber() {
        superDrugPage.parseMaxPageNum();
        return superDrugPage.getMaxPageNum();
    }

    @Override
    public Elements getAds() {
        superDrugPage.parseListOfAdElements();
        return superDrugPage.getAdElements();
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
