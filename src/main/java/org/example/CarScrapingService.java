package org.example;

import org.example.Car;
import org.example.CarParser;
import org.example.CarDetailsPage;
import org.example.CarSearchPage;
import org.example.DriverFactory;
import org.example.SeleniumUtils;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

public class CarScrapingService {

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
        WebDriver driver = DriverFactory.createDriver();
        try {
            SeleniumUtils.hideWebDriverFlag(driver);

            CarDetailsPage page = new CarDetailsPage(driver);
            CarParser parser = new CarParser();

            List<Car> cars = new ArrayList<>();
            for (String link : links) {
                page.open(link);
                cars.add(parser.parse(link, page));
            }
            return cars;
        } finally {
            driver.quit();
        }
    }
}
