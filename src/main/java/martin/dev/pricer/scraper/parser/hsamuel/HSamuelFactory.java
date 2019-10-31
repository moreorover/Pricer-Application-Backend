package martin.dev.pricer.scraper.parser.hsamuel;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import martin.dev.pricer.scraper.parser.FactoryImpl;
import martin.dev.pricer.scraper.parser.hsamuel.parser.HSamuelAdElement;
import martin.dev.pricer.scraper.parser.hsamuel.parser.HSamuelPage;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class HSamuelFactory extends FactoryImpl<HSamuelPage> {

    public HSamuelFactory(Document pageContentInJsoup) {
        setPage(new HSamuelPage(pageContentInJsoup));
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
            HSamuelAdElement hSamuelAdElement = new HSamuelAdElement(adInHtml);
            itemDtoList.add(hSamuelAdElement.parseAll());
        });

        return itemDtoList;
    }
}
