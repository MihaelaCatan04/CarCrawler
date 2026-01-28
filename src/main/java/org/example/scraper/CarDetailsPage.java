package org.example.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

// We're restricted to Romanian
public class CarDetailsPage {
    private final static String CAR_NAME_PATH = "//li[.//span[text()='Marcă']]//a";
    private final static String MODEL_NAME_PATH = "//li[.//span[text()='Model']]//a";
    private final static String GENERATION_PATH = "//li[.//span[text()='Generație']]//a";
    private final static String YEAR_PATH = "//li[.//span[text()='An de fabricație']]//span[contains(@class,'value')]";
    private final static String MILEAGE_PATH = "//li[.//span[text()='Rulaj']]//span[contains(@class,'value')]";
    private final static String PRICE_PATH = "//div[@data-sentry-component='Price']//span[contains(@class,'main')]";
    private final static Logger logger = LoggerFactory.getLogger(CarDetailsPage.class);
    private final static int TIMEOUT_DURATION = 30;
    private final static String USER_AGENT = "\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) \" +\n" +
            "                                \"AppleWebKit/537.36 (KHTML, like Gecko) \" +\n" +
            "                                \"Chrome/121.0.0.0 Safari/537.36\""
    private final HttpClient httpClient;
    private HttpResponse<String> response;

    public CarDetailsPage(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void createRequest(String link) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(link))
                .timeout(Duration.ofSeconds(TIMEOUT_DURATION))
                .header("User-Agent", USER_AGENT)
                .GET()
                .build();
        try {
            this.response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            logger.error("Error while sending request", e);
        }
    }


    private String getTextOrNull(String xpath) {
        Document doc = Jsoup.parse(response.body());
        Elements elements = doc.selectXpath(xpath);
        var text = elements.first().text();
        if (elements.isEmpty()) return null;
        return text.isBlank() ? null : text;
    }

    public String getName() {
        return getTextOrNull(CAR_NAME_PATH);
    }

    public String getModel() {
        return getTextOrNull(MODEL_NAME_PATH);
    }

    public String getGeneration() {
        return getTextOrNull(GENERATION_PATH);
    }

    public String getYearText() {
        return getTextOrNull(YEAR_PATH);
    }

    public String getMileageText() {
        return getTextOrNull(MILEAGE_PATH);
    }

    public String getPriceText() {
        return getTextOrNull(PRICE_PATH);
    }

}
