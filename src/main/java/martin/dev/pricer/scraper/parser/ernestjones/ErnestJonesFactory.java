package martin.dev.pricer.scraper.parser.ernestjones;

import martin.dev.pricer.scraper.model.ParsedItemDto;
import martin.dev.pricer.scraper.parser.ernestjones.parser.EernestJonesAdElement;
import martin.dev.pricer.scraper.parser.ernestjones.parser.ErnestJonesPage;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class ErnestJonesFactory {

    private ErnestJonesPage ernestJonesPage;

    public ErnestJonesFactory(Document pageContentInJsoup) {
        this.ernestJonesPage = new ErnestJonesPage(pageContentInJsoup);
    }

    public int getMaxPageNumber() {
        ernestJonesPage.parseMaxPageNum();
        return ernestJonesPage.getMaxPageNum();
    }

    private Elements getAds() {
        ernestJonesPage.parseListOfAdElements();
        return ernestJonesPage.getAdElements();
    }

    public List<ParsedItemDto> getParsedAds() {
        List<ParsedItemDto> itemDtoList = new ArrayList<>();

        getAds().forEach(adInHtml -> {
            EernestJonesAdElement EernestJonesAdElement = new EernestJonesAdElement(adInHtml);
            itemDtoList.add(EernestJonesAdElement.parseAll());
        });

        return itemDtoList;
    }
}
