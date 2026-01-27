package org.example.scraper;

import org.example.utils.SeleniumUtils;
import org.example.model.Car;
import org.example.parser.CarParser;
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
