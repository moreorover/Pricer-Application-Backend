package martin.dev.pricer.scraper.parser.hsamuel;

import martin.dev.pricer.scraper.model.ParsedItemDto;
import martin.dev.pricer.scraper.parser.Factory;
import martin.dev.pricer.scraper.parser.hsamuel.parser.HSamuelAdElement;
import martin.dev.pricer.scraper.parser.hsamuel.parser.HSamuelPage;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class HSamuelFactory implements Factory {

    private HSamuelPage hSamuelPage;

    public HSamuelFactory(Document pageContentInJsoup) {
        this.hSamuelPage = new HSamuelPage(pageContentInJsoup);
    }

    @Override
    public int getMaxPageNumber() {
        hSamuelPage.parseMaxPageNum();
        return hSamuelPage.getMaxPageNum();
    }

    @Override
    public Elements getAds() {
        hSamuelPage.parseListOfAdElements();
        return hSamuelPage.getAdElements();
    }

    @Override
    public List<ParsedItemDto> getParsedAds() {
        List<ParsedItemDto> itemDtoList = new ArrayList<>();

        getAds().forEach(adInHtml -> {
            HSamuelAdElement hSamuelAdElement = new HSamuelAdElement(adInHtml);
            itemDtoList.add(hSamuelAdElement.parseAll());
        });

        return itemDtoList;
    }
}
