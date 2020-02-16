package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.fabric.product.DealProcessor;
import martin.dev.pricer.data.model.store.StoreUrl;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class WatchoScraper extends Scraper {
    private DealProcessor dealProcessor;

    public WatchoScraper(StoreUrl storeUrl, DealProcessor dealProcessor) {
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
        String[] x = full.split("&page=");
        return x[0] + "&page=" + pageNum;
    }

    @Override
    public Elements parseListOfAdElements(Document pageContentInJsoupHtml) {
        return pageContentInJsoupHtml.select("li[class=product]");
    }

    @Override
    public int parseMaxPageNum(Document pageContentInJsoupHtml) {
        Elements paginationElements = pageContentInJsoupHtml.select("li[class^=pagination-item]");
        if (paginationElements.size() == 0){
            return 1;
        }
        Element lastPaginationElement = paginationElements.get(paginationElements.size() - 2);
        return Integer.parseInt(lastPaginationElement.text());
    }

    @Override
    public String parseTitle(Element adInJsoupHtml){
        Element titleElement = adInJsoupHtml.selectFirst("h4[class=card-title]");
        return titleElement.text();
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) {
        return "WO_" + adInJsoupHtml.selectFirst("article").attr("data-entity-id");
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) {
        String priceString = adInJsoupHtml.selectFirst("span[class=price price--withTax]").text();
        priceString = priceString.replaceAll("[^\\d.]", "");
        return Double.parseDouble(priceString);
    }

    @Override
    public String parseImage(Element adInJsoupHtml) {
        Element imgElement = adInJsoupHtml.selectFirst("img[class^=card-image]");
        return imgElement.attr("data-src");
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) {
        Element urlElement = adInJsoupHtml.selectFirst("a");
        return urlElement.attr("href");
    }
}
