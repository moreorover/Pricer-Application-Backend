package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.fabric.product.DealProcessor;
import martin.dev.pricer.data.model.store.StoreUrl;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class GoldSmithsParser implements ParserMongo {

    @Override
    public String makeNextPageUrl(String url, int pageNum) {
//        https://www.goldsmiths.co.uk/c/Watches/Mens-Watches/filter/Page_2/Psize_96/Show_Page/
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
        return (adsCount + 96 - 1) / 96;
    }

    @Override
    public String parseTitle(Element adInJsoupHtml) {
        Element titleElement = adInJsoupHtml.selectFirst("div[class=product-title]");
        return titleElement.text();
    }

    @Override
    public String parseUpc(Element adInJsoupHtml) {
        Element aElement = adInJsoupHtml.selectFirst("a");
        return "GS_" + aElement.attr("id");
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
        if (imgSrc.endsWith(".jpg")){
            return imgSrc;
        }
        return "";
    }

    @Override
    public String parseUrl(Element adInJsoupHtml) {
        Element aElement = adInJsoupHtml.selectFirst("a");
        String urlBase = "https://www.goldsmiths.co.uk";
        return urlBase + aElement.attr("href");
    }
}
