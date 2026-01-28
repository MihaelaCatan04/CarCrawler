package org.example.scraper;

import org.example.model.Car;
import org.example.parser.CarParser;
import org.example.utils.SeleniumUtils;
import org.openqa.selenium.WebDriver;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.http.HttpClient;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CarScrapingService {
    private static final int DURATION = 20;

    public List<String> collectCarLinks(String baseUrl) {
        WebDriver driver = DriverFactory.createDriver();
        try {
            SeleniumUtils.hideWebDriverFlag(driver);

            CarSearchPage searchPage = new CarSearchPage(driver);
            searchPage.open(baseUrl);
            searchPage.selectCarsCategory();
            searchPage.applyFilters();

            return searchPage.collectCarLinks();
        } finally {
            driver.quit();
        }
    }

    public List<Car> scrapeCars(List<String> links) {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        HttpClient httpClient = HttpClient.newBuilder()
                .cookieHandler(cookieManager)
                .connectTimeout(Duration.ofSeconds(DURATION))
                .build();
        try {
            CarDetailsPage page = new CarDetailsPage(httpClient);
            CarParser parser = new CarParser();

            List<Car> cars = new ArrayList<>();
            for (String link : links) {
                page.createRequest(link);
                cars.add(parser.parse(link, page));
            }
            return cars;
        } catch (Exception e) {
            throw new RuntimeException("ERROR_SCRAPE_CARS", e);
        }
    }
}
