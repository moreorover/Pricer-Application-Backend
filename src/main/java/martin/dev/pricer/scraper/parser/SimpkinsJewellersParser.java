package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.Parser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class SimpkinsJewellersParser implements Parser {

    public final String NAME = "Simpkins Jewellers";
    public final String PREFIX = "SJ_";
    public final String BASE_URL = "https://simpkinsjewellers.co.uk";

    @Override
    public String makeNextPageUrl(String url, int pageNum) {
        String[] x = url.split("page=");
        return x[0] + "page=" + pageNum;
    }

    @Override
    public Elements parseListOfAdElements(Document pageContentInJsoupHtml) {
        return pageContentInJsoupHtml.select("div.product.clearfix.product-hover");
    }

    @Override
    public int parseMaxPageNum(Document pageContentInJsoupHtml) {
        Element paginationElement = pageContentInJsoupHtml.selectFirst("div.row.pagination-results");
        Elements paginationElements = paginationElement.children();
        Element lastPageElement = paginationElements.get(paginationElements.size() - 1);
        String paginationText = lastPageElement.text().split(" \\(")[1];
        String lastPageNumber = paginationText.replaceAll("[^\\d.]", "");
        int maxPageNum = Integer.parseInt(lastPageNumber);
        log.info("Found " + "?" + " ads to scrape, a total of " + maxPageNum + " pages.");
        return maxPageNum;
    }

    @Override
    public String parseTitle(Element adInJsoupHtml) {
        Element imageDiv = adInJsoupHtml.selectFirst("div[class=image]");
        return imageDiv.selectFirst("img").attr("alt");
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) {
        Element hoverElement = adInJsoupHtml.selectFirst("div[class=only-hover]");
        Element addToCart = hoverElement.selectFirst("a");
        String upc = addToCart.attr("onclick");
        upc = upc.replaceAll("[^\\d]", "");
        return PREFIX + upc;
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) {
        double price = -2.0;
        Element priceElement = adInJsoupHtml.selectFirst("div[class=price]");
        Elements priceElements = priceElement.children();
        if(priceElements.size() == 0){
            String priceText = priceElement.text().replaceAll("[^\\d.]", "");
            price = Double.parseDouble(priceText);
        } else if (priceElements.size() == 2) {
            Element priceEl = priceElement.selectFirst("span[class=price-new]");
            String priceText = priceEl.text().replaceAll("[^\\d.]", "");
            price = Double.parseDouble(priceText);
        }
        return price;
    }

    @Override
    public String parseImage(Element adInJsoupHtml) {
        Element imageDiv = adInJsoupHtml.selectFirst("div[class=image]");
        return imageDiv.selectFirst("img").attr("src");
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) {
        Element imageDiv = adInJsoupHtml.selectFirst("div[class=image]");
        return imageDiv.selectFirst("a").attr("href");
    }

    @Override
    public String getParserName() {
        return NAME;
    }
}
