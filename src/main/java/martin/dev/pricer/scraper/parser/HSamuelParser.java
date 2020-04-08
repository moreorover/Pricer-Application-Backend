package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.ParserI;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class HSamuelParser implements ParserI {

    public final String NAME = "H. Samuel";
    public final String PREFIX = "HS_";
    public final String BASE_URL = "https://www.hsamuel.co.uk";

    @Override
    public String makeNextPageUrl(String url, int pageNum) {
        String[] x = url.split("Pg=");
        return x[0] + "Pg=" + pageNum;
    }

    @Override
    public Elements parseListOfAdElements(Document pageContentInJsoupHtml) {
        return pageContentInJsoupHtml.select("li[class^=product-tile-list__item]");
    }

    @Override
    public int parseMaxPageNum(Document pageContentInJsoupHtml) {
        Element paginationBlockElement = pageContentInJsoupHtml.selectFirst("ol[class*=pageNumbers]");
        Elements paginationButtons = paginationBlockElement.select("li");
        Element lastPageElement = paginationButtons.get(5);
        String lastPageText = lastPageElement.text();
        int maxPageNum = Integer.parseInt(lastPageText);
        log.info("Found " + "?" + " ads to scrape, a total of " + maxPageNum + " pages.");
        return maxPageNum;
    }

    @Override
    public String parseTitle(Element adInJsoupHtml) {
        return adInJsoupHtml.select("p[class=product-tile__description]").text();
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) {
//        Element upcElement = adInJsoupHtml.selectFirst("meta");
        String url = parseUrl(adInJsoupHtml);
        String[] strings = url.split("/d/");
        strings = strings[1].split("/");
        return PREFIX + strings[0];
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
        return BASE_URL + element.attr("href");
    }

    @Override
    public String getParserName() {
        return NAME;
    }
}
