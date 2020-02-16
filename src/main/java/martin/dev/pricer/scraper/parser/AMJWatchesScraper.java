package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.fabric.product.DealProcessor;
import martin.dev.pricer.data.model.store.StoreUrl;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class AMJWatchesScraper extends Scraper {

    private DealProcessor dealProcessor;

    public AMJWatchesScraper(StoreUrl storeUrl, DealProcessor dealProcessor) {
        super(storeUrl);
        this.dealProcessor = dealProcessor;
    }

    @Override
    public void scrapePages() {
        int maxPageNum = parseMaxPageNum(super.getPageContentInJsoupHtml());

        int currentRotation = 1;

        while (currentRotation <= maxPageNum) {
            log.info("Parsing page: " + makeNextPageUrl(currentRotation));

            Elements parsedItemElements = parseListOfAdElements(super.getPageContentInJsoupHtml());
            super.htmlToParsedDtos(parsedItemElements);

            super.getParsedItemDtos().forEach(parsedItemDto -> this.dealProcessor.workOnData(parsedItemDto, super.getStoreUrl()));

            String nexUrlToScrape = makeNextPageUrl(++currentRotation);
            super.fetchUrlContents(nexUrlToScrape);
        }
    }

    @Override
    public String makeNextPageUrl(int pageNum) {
        String full = getStoreUrl().getUrlLink();
        String[] x = full.split("page=");
        return x[0] + "page=" + pageNum;
    }

    @Override
    public Elements parseListOfAdElements(Document pageContentInJsoupHtml) {
        return pageContentInJsoupHtml.select("div[class=watch-sec]");
    }

    @Override
    public int parseMaxPageNum(Document pageContentInJsoupHtml) {
        Element productsCountElement = pageContentInJsoupHtml.selectFirst("div[class=items-on-page]");
        String countText = productsCountElement.children().last().text();
        countText = countText.replaceAll("[^\\d.]", "");
        int adsCount = Integer.parseInt(countText);
        return (adsCount + 40 - 1) / 40;
    }

    @Override
    public String parseTitle(Element adInJsoupHtml) {
        Element imgElement = adInJsoupHtml.selectFirst("div[class=watch-image]");
        imgElement = imgElement.selectFirst("a");
        return imgElement.attr("title");
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) {
        try {
            Element imgElement = adInJsoupHtml.selectFirst("div[class=watch-image]");
            imgElement = imgElement.selectFirst("a");
            String url = imgElement.attr("href");
            url = url.split(".uk/")[1];
            return "AMJW_" + url;
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) {
        Element detailsElement = adInJsoupHtml.selectFirst("div[class=watch-details]");

        String priceString = detailsElement.children().last().text().replaceAll("[^\\d.]", "");
        return Double.parseDouble(priceString);
    }

    @Override
    public String parseImage(Element adInJsoupHtml) {
        Element imgElement = adInJsoupHtml.selectFirst("div[class=watch-image]");
        imgElement = imgElement.selectFirst("a");
        imgElement = imgElement.selectFirst("img");
        return imgElement.attr("data-src");
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) {
        Element imgElement = adInJsoupHtml.selectFirst("div[class=watch-image]");
        imgElement = imgElement.selectFirst("a");
        return imgElement.attr("href");
    }
}
