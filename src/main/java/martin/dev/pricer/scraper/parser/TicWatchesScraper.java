package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.fabric.product.DealProcessor;
import martin.dev.pricer.data.model.store.StoreUrl;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class TicWatchesScraper extends Scraper {

    private DealProcessor dealProcessor;

    public TicWatchesScraper(StoreUrl storeUrl, DealProcessor dealProcessor) {
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
            System.out.println(parsedItemElements.get(0).outerHtml());
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
        Elements elements = pageContentInJsoupHtml.select("div[class^=product]");
        super.validateElements(elements);
        return elements;
    }

    @Override
    public int parseMaxPageNum(Document pageContentInJsoupHtml) {
        Element paginationBlockElement = pageContentInJsoupHtml.selectFirst("div[class=product-listings__top__view-all]");
        String totalResults = paginationBlockElement.text().replaceAll("[^\\d.]", "");
        int adsCount = Integer.parseInt(totalResults);
        return (adsCount + 24 - 1) / 24;
    }

    @Override
    public String parseTitle(Element adInJsoupHtml) {
        return adInJsoupHtml.selectFirst("a").attr("title");
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) {
        String upc = adInJsoupHtml.attr("data-infid");
        return "TIC_" + upc;
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) {
        String priceString = adInJsoupHtml.select("span[class=product-content__price--inc]").text();
        priceString = priceString.replaceAll("[^\\d.]", "");
        return Double.parseDouble(priceString);
    }

    @Override
    public String parseImage(Element adInJsoupHtml) {
        Element imgElement = adInJsoupHtml.selectFirst("img");
        return "https://www.ticwatches.co.uk" + imgElement.attr("src");
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) {
        return adInJsoupHtml.selectFirst("a").attr("href");
    }
}
