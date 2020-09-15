package martin.dev.pricer.scraper.parser;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.scraper.Parser;
import martin.dev.pricer.scraper.Scraper;
import martin.dev.pricer.scraper.ScraperTools;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class GoldSmithsParser implements Parser {
    @Override
    public void parseListOfAdElements(Scraper scraper) {
        try {
            Elements parsedElements = scraper.getPageHtmlDocument().select("div[class=product]");
            Validate.notNull(parsedElements, "Elements should not be null");
            scraper.setAds(parsedElements);
        } catch (NullPointerException e) {
            log.error(e.getMessage());
            scraper.setAds(new Elements());
        }
    }

    @Override
    public String parseAdTitle(Element adInJsoupHtml) {
        try {
            Element titleElement = adInJsoupHtml.selectFirst("div[class=product-title]");
            String title = titleElement.text();
            return title;
        } catch (NullPointerException e) {
            return "";
        }
    }

    @Override
    public String parseAdUpc(Element adInJsoupHtml) {
        try {
            Element aElement = adInJsoupHtml.selectFirst("a");
            String upc = aElement.attr("id");
            return "GS_" + upc;
        } catch (NullPointerException e) {
            return "";
        }
    }

    @Override
    public Double parseAdPrice(Element adInJsoupHtml) {
        try {
            Element priceElement = adInJsoupHtml.selectFirst("div[class=prodPrice]");
            String priceString = priceElement.text();
            Double price = ScraperTools.parseDoubleFromString(priceString);
            return price;
        } catch (NullPointerException e) {
            return 0.0;
        }
    }

    @Override
    public String parseAdImage(Element adInJsoupHtml) {
        try {
            Element imgElement = adInJsoupHtml.selectFirst("img");
            String imgSrc = imgElement.attr("src");
            if (imgSrc.endsWith(".jpg")) {
                return imgSrc;
            }
            return "";
        } catch (NullPointerException e) {
            return "";
        }
    }

    @Override
    public String parseAdUrl(Element adInJsoupHtml) {
        try {
            Element aElement = adInJsoupHtml.selectFirst("a");
            String url = aElement.attr("abs:href");

            return url;
        } catch (NullPointerException e) {
            return "";
        }
    }

    @Override
    public boolean nextPageAvailable(Document document) {
        Element element = document.selectFirst("ul[class=sort-pagination unstyled clearfix]");
        return element.childNodes().toString().contains("class=\"next\"");
    }

    @Override
    public void nextPageUrl(Scraper scraper) {
        // https://www.goldsmiths.co.uk/c/Watches/Ladies-Watches/filter/Page_1/Psize_96/Show_Page/
        String[] x = scraper.getCurrentPageUrl().split("Page_");
        String[] y = x[1].split("/");
        int pageNumber = ScraperTools.parseIntegerFromString(y[0]) + 1;
        scraper.setCurrentPageNumber(pageNumber);
        scraper.setCurrentPageUrl(x[0] + "Page_" + pageNumber + "/Psize_96/Show_Page/");
    }
}
