package martin.dev.pricer.state;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.model.ParsedItemDto;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ScraperParsingHtmlState implements ScraperState {
    @Override
    public void fetchUrl(Scraper scraper) {

    }

    @Override
    public void fetchHtml(Scraper scraper) {

    }

    @Override
    public void validateResponse(Scraper scraper) {
        Validate.notNull(scraper.getPageHtmlDocument(), "Page HTML Document can not be NULL!");
        scraper.parseResponseToAds();
    }

    @Override
    public void parseResponseToAds(Scraper scraper) {
        scraper.setAds(scraper.getScraperParser().parseListOfAdElements(scraper.getPageHtmlDocument()));
        scraper.validateAds();
    }

    @Override
    public void validateAds(Scraper scraper) {
        Validate.notNull(scraper.getAds(), "Page HTML Document can not be NULL!");
        scraper.parseAdsToItems();
    }

    @Override
    public void parseAdsToItems(Scraper scraper) {
        Elements ads = scraper.getAds();
        List<ParsedItemDto> parsedItemDtos = ads.stream()
                .map(element -> {
                    ParsedItemDto parsedItemDto = new ParsedItemDto();
                    parsedItemDto.setFoundTime(LocalDateTime.now());
                    parsedItemDto.setTitle(scraper.getScraperParser().parseAdTitle(element));
                    parsedItemDto.setUrl(scraper.getScraperParser().parseAdUrl(element));
                    parsedItemDto.setImg(scraper.getScraperParser().parseAdImage(element));
                    parsedItemDto.setUpc(scraper.getScraperParser().parseAdUpc(element));
                    parsedItemDto.setPrice(scraper.getScraperParser().parseAdPrice(element));
                    parsedItemDto.setUrlFound(scraper.getCurrentPageUrl());
                    parsedItemDto.setUrlObject(scraper.getUrl());
                    return parsedItemDto;
                })
                .filter(ParsedItemDto::isValid)
//                .filter(parsedItemDto -> !parsedItemDto.isValid())
                .collect(Collectors.toList());
        scraper.getItems().addAll(parsedItemDtos);
        scraper.changeState(State.ProcessingAds);
        scraper.processItems();
    }

    @Override
    public void validateItems(Scraper scraper) {

    }

    @Override
    public void processItems(Scraper scraper) {

    }

    @Override
    public void writeItems(Scraper scraper) {

    }

    @Override
    public void sendItems(Scraper scraper) {

    }

    @Override
    public void nextPage(Scraper scraper) {
        Document doc = scraper.getPageHtmlDocument();
        if (scraper.getScraperParser().nextPageAvailable(doc)) {
            scraper.nextPageUrl();
            scraper.changeState(State.FetchingHtml);
            scraper.fetchHtml();
        } else {
            log.info("That was the last page.");
        }
    }
}