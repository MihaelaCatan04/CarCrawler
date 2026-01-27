package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

// We're restricted to Romanian
public class CarDetailsPage {

    private final WebDriver driver;
    private final static String CAR_NAME_PATH = "//li[.//span[text()='Marcă']]//a";
    private final static String MODEL_NAME_PATH = "//li[.//span[text()='Model']]//a";
    private final static String GENERATION_PATH = "//li[.//span[text()='Generație']]//a";
    private final static String YEAR_PATH = "//li[.//span[text()='An de fabricație']]//span[contains(@class,'value')]";
    private final static String MILEAGE_PATH = "//li[.//span[text()='Rulaj']]//span[contains(@class,'value')]";
    private final static String PRICE_PATH = "//div[@data-sentry-component='Price']//span[contains(@class,'main')]";

    public CarDetailsPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open(String link) {
        driver.get(link);
    }

    private String getTextOrNull(By locator) {
        var elements = driver.findElements(locator);
        if (elements.isEmpty()) return null;
        String text = elements.getFirst().getText();
        return text.isBlank() ? null : text;
    }

    public String getName() {
        return getTextOrNull(
                By.xpath(CAR_NAME_PATH)
        );
    }

    public String getModel() {
        return getTextOrNull(
                By.xpath(MODEL_NAME_PATH)
        );
    }

    public String getGeneration() {
        return getTextOrNull(
                By.xpath(GENERATION_PATH)
        );
    }

    public String getYearText() {
        return getTextOrNull(
                By.xpath(YEAR_PATH)
        );
    }

    public String getMileageText() {
        return getTextOrNull(
                By.xpath(MILEAGE_PATH)
        );
    }

    public String getPriceText() {
        return getTextOrNull(
                By.xpath(PRICE_PATH)
        );
    }

}
