package org.example;

import io.github.cdimascio.dotenv.Dotenv;
import org.example.SeleniumUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;



public class CarSearchPage {
    Dotenv dotenv = Dotenv.load();

    private final WebDriver driver;
    private final WebDriverWait wait;
    private String car;
    private String model;
    private String generation;

    private void clickOptionByText(WebDriver driver, WebDriverWait wait, String text) {
        By option = By.xpath("//label[normalize-space()='" + text + "']");
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(option));
        SeleniumUtils.safeClick(driver, element);
    }

    private void clickOptionByCSS(WebDriver driver, WebDriverWait wait, String text) {
        By option = By.cssSelector(text);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(option));
        SeleniumUtils.safeClick(driver, element);
    }

    private void clickOptionById(WebDriver driver, WebDriverWait wait, String text) {
        By option = By.id(text);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(option));
        SeleniumUtils.safeClick(driver, element);
    }


    public CarSearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.car = dotenv.get("CAR");
        this.model = dotenv.get("MODEL");
        this.generation = dotenv.get("GENERATION");
    }

    public void open(String link) {
        driver.get(link);
    }

    public void selectCarsCategory() {
        clickOptionByCSS(driver, wait, "button[data-testid='categories-toggle']");
        clickOptionByCSS(driver, wait, "li[data-url='transport']");
        clickOptionByCSS(driver, wait, "a[data-subcategory='659']");
    }

    public void applyFilters() {
        clickOptionByCSS(driver, wait, "button[data-testid='show_all_btn']");
        clickOptionByCSS(driver, wait, "a.introjs-skipbutton");
        clickOptionByText(driver, wait, car);
        clickOptionByText(driver, wait, model);
        clickOptionByText(driver, wait, generation);
        clickOptionById(driver, wait, "apply-filters-btn");
    }

    public List<String> collectCarLinks() {
        List<String> carLinks = new ArrayList<>();

        while (true) {
            WebElement adList = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("div[data-sentry-component='AdList']")
            ));

            List<WebElement> carLinksElements = adList.findElements(
                    By.cssSelector("div.AdPhoto_wrapper__gAOIH a.AdPhoto_image__BMixw")
            );

            for (WebElement linkElement : carLinksElements) {
                carLinks.add(linkElement.getAttribute("href"));
            }

            List<WebElement> nextButtons = driver.findElements(By.cssSelector(
                    "button.Pagination_pagination__container__buttons__wrapper__icon__next__A22Rc"
            ));

            if (nextButtons.isEmpty() || !nextButtons.get(0).isEnabled()) {
                break;
            }

            // Click next and wait for page to change
            WebElement firstCar = carLinksElements.get(0); // store first element to wait for staleness
            WebElement next = wait.until(ExpectedConditions.elementToBeClickable(nextButtons.get(0)));
            SeleniumUtils.safeClick(driver, next);

            // Wait until the first car becomes stale (page changed)
            wait.until(ExpectedConditions.stalenessOf(firstCar));
        }

        return carLinks;
    }

}
