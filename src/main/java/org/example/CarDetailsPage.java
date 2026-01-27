package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

// We're restricted to Romanian
public class CarDetailsPage {

    private final WebDriver driver;

    public CarDetailsPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open(String link) {
        driver.get(link);
    }

    private String getTextOrNull(By locator) {
        var elements = driver.findElements(locator);
        if (elements.isEmpty()) return null;
        String text = elements.get(0).getText();
        return text.isBlank() ? null : text;
    }

    public String getName() {
        return getTextOrNull(
                By.xpath("//li[.//span[text()='Marcă']]//a")
        );
    }

    public String getModel() {
        return getTextOrNull(
                By.xpath("//li[.//span[text()='Model']]//a")
        );
    }

    public String getGeneration() {
        return getTextOrNull(
                By.xpath("//li[.//span[text()='Generație']]//a")
        );
    }

    public String getYearText() {
        return getTextOrNull(
                By.xpath("//li[.//span[text()='An de fabricație']]//span[contains(@class,'value')]")
        );
    }

    public String getMileageText() {
        return getTextOrNull(
                By.xpath("//li[.//span[text()='Rulaj']]//span[contains(@class,'value')]")
        );
    }

    public String getPriceText() {
        return getTextOrNull(
                By.xpath("//div[@data-sentry-component='Price']//span[contains(@class,'main')]")
        );
    }

}
