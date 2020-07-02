package martin.dev.pricer.scraper.client;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class HttpClient {

    public static Document readContentInJsoupDocument(String target) {
        StringBuilder stingBuilderHtml = readHtmlContent(target);
        return Jsoup.parse(stingBuilderHtml.toString());
    }

    private static StringBuilder readHtmlContent(String target) {
        StringBuilder response = new StringBuilder();
        try {
            URL obj = new URL(target);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");

            if (con.getResponseCode() < 400) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            } else {
                log.error("Response code invalid: " + con.getResponseCode());
                log.error(con.getResponseMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
