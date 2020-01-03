package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.fabric.product.DealProcessor;
import martin.dev.pricer.data.model.store.StoreUrl;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class HSamuelScraper extends Scraper {

    private DealProcessor dealProcessor;

    public HSamuelScraper(StoreUrl storeUrl, DealProcessor dealProcessor) {
        super(storeUrl);
        this.dealProcessor = dealProcessor;
    }

    @Override
    public void scrapePages(StoreUrl storeUrl) {

        int maxPageNum = parseMaxPageNum(super.getPageContentInJsoupHtml());

        int currentRotation = 1;

        while (currentRotation <= maxPageNum){
            log.info("Parsing page: " + makeNextPageUrl(currentRotation));

            Elements parsedItemElements = parseListOfAdElements(getPageContentInJsoupHtml());
            super.htmlToParsedDtos(parsedItemElements);

            super.getParsedItemDtos().forEach(parsedItemDto -> this.dealProcessor.workOnData(parsedItemDto, storeUrl));

            String nexUrlToScrape = makeNextPageUrl(++currentRotation);
            super.fetchUrlContents(nexUrlToScrape);
        }
    }

    @Override
    public String makeNextPageUrl(int pageNum) {
        String full = getStoreUrl().getUrlLink();
        String[] x = full.split("Pg=");
        return x[0] + "Pg=" + pageNum;
    }

    @Override
    public Elements parseListOfAdElements(Document pageContentInJsoupHtml) {
        Elements elements = pageContentInJsoupHtml.select("li[class^=product-tile-list__item]");
        super.validateElements(elements);
        return elements;
//        return pageContentInJsoupHtml.select("div.product-tile.js-product-item");
    }

    @Override
    public int parseMaxPageNum(Document pageContentInJsoupHtml) {
        Element paginationBlockElement = pageContentInJsoupHtml.selectFirst("ol[class*=pageNumbers]");
        Elements paginationButtons = paginationBlockElement.select("li");
        Element lastPageElement = paginationButtons.get(5);
        String lastPageText = lastPageElement.text();
        return Integer.parseInt(lastPageText);
    }

    @Override
    public String parseTitle(Element adInJsoupHtml) {
        return adInJsoupHtml.select("p[class=product-tile__description]").text();
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) {
//        Element upcElement = adInJsoupHtml.selectFirst("meta");
        String url = this.parseUrl(adInJsoupHtml);
        String[] strings = url.split("/d/");
        strings = strings[1].split("/");
        return "HS_" + strings[0];
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) {
        String priceString = adInJsoupHtml.select("p[class*=current-price]").text();
        priceString = priceString.replaceAll("[^\\d.]", "");
        return Double.parseDouble(priceString);
    }

    @Override
    public String parseImage(Element adInJsoupHtml) {
        Element imgElement = adInJsoupHtml.selectFirst("img[class^=product-tile__image]");
        return imgElement.attr("data-src");
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) {
        Element element = adInJsoupHtml.select("a").first();
        String urlBase = "https://www.hsamuel.co.uk";
        return urlBase + element.attr("href");
    }
}
