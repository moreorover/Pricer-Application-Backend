package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.ParserI;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class GoldSmithsParser implements ParserI {

    public final String NAME = "Gold Smiths";
    public final String PREFIX = "GS_";
    public final String BASE_URL = "https://www.goldsmiths.co.uk";

    @Override
    public String makeNextPageUrl(String url, int pageNum) {
        String[] x = url.split("Page_");
        return x[0] + "Page_" + pageNum + "/Psize_96/Show_Page/";
    }

    @Override
    public Elements parseListOfAdElements(Document pageContentInJsoupHtml) {
        return pageContentInJsoupHtml.select("div[class=product]");
    }

    @Override
    public int parseMaxPageNum(Document pageContentInJsoupHtml) {
        Element resultsElement = pageContentInJsoupHtml.selectFirst("p[class^=showAllResults]");
        resultsElement = resultsElement.selectFirst("span[class=bold]");
        String countString = resultsElement.text();
        countString = countString.replaceAll("[^\\d.]", "");
        int adsCount = Integer.parseInt(countString);
        int maxPageNum = (adsCount + 96 - 1) / 96;
        log.info("Found " + adsCount + "ads to scrape, a total of " + maxPageNum + " pages.");
        return maxPageNum;
    }

    @Override
    public String parseTitle(Element adInJsoupHtml) {
        Element titleElement = adInJsoupHtml.selectFirst("div[class=product-title]");
        if (titleElement != null){
            return titleElement.text();
        } else {
            return "";
        }
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) {
        Element aElement = adInJsoupHtml.selectFirst("a");
        return PREFIX + aElement.attr("id");
    }

    @Override
    public Double parsePrice(Element adInJsoupHtml) {
        Element priceElement = adInJsoupHtml.selectFirst("div[class=prodPrice]");
        String priceString = priceElement.text().replaceAll("[^\\d.]", "");
        return Double.parseDouble(priceString);
    }

    @Override
    public String parseImage(Element adInJsoupHtml) {
        Element imgElement = adInJsoupHtml.selectFirst("img");
        String imgSrc = imgElement.attr("src");
        if (imgSrc.endsWith(".jpg")) {
            return imgSrc;
        }
        return "";
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) {
        Element aElement = adInJsoupHtml.selectFirst("a");
        return BASE_URL + aElement.attr("href");
    }

    @Override
    public String getParserName() {
        return NAME;
    }
}
