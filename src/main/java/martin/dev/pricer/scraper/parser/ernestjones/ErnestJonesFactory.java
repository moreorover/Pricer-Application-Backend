package martin.dev.pricer.scraper.parser.ernestjones;

import martin.dev.pricer.scraper.model.ParsedItemDto;
import martin.dev.pricer.scraper.parser.Factory;
import martin.dev.pricer.scraper.parser.ernestjones.parser.EernestJonesAdElement;
import martin.dev.pricer.scraper.parser.ernestjones.parser.ErnestJonesPage;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class ErnestJonesFactory implements Factory {

    private ErnestJonesPage ernestJonesPage;

    public ErnestJonesFactory(Document pageContentInJsoup) {
        this.ernestJonesPage = new ErnestJonesPage(pageContentInJsoup);
    }

    @Override
    public int getMaxPageNumber() {
        ernestJonesPage.parseMaxPageNum();
        return ernestJonesPage.getMaxPageNum();
    }

    @Override
    public Elements getAds() {
        ernestJonesPage.parseListOfAdElements();
        return ernestJonesPage.getAdElements();
    }

    @Override
    public List<ParsedItemDto> getParsedAds() {
        List<ParsedItemDto> itemDtoList = new ArrayList<>();

        getAds().forEach(adInHtml -> {
            EernestJonesAdElement EernestJonesAdElement = new EernestJonesAdElement(adInHtml);
            itemDtoList.add(EernestJonesAdElement.parseAll());
        });

        return itemDtoList;
    }
}
