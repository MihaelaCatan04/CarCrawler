package org.example;

import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


public class CarSearchPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    Dotenv dotenv = Dotenv.load();
    private final String car;
    private final String model;
    private final String generation;
    private final String CAR_NAME_MENU_PATH = "//label[normalize-space()='";
    private final String CATEGORIES_BUTTON_PATH = "button[data-testid='categories-toggle']";
    private final String TRANSPORTATION_BUTTON_PATH = "li[data-url='transport']";
    private final String CAR_CATEGORY_BUTTON = "a[data-subcategory='659']";
    private final String SHOW_ALL_BUTTON = "button[data-testid='show_all_btn']";
    private final String SKIP_BUTTON = "a.introjs-skipbutton";
    private final String APPLY_FILTERS_BUTTON = "apply-filters-btn";
    private final String CAR_LISTING_PATH = "div[data-sentry-component='AdList']";
    private final String CAR_ELEMENT_PATH = "div.AdPhoto_wrapper__gAOIH a.AdPhoto_image__BMixw";
    private final String HREF_ATTRIBUTE = "href";
    private final String NEXT_BUTTON_PATH = "button.Pagination_pagination__container__buttons__wrapper__icon__next__A22Rc";


    public CarSearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.car = dotenv.get("CAR");
        this.model = dotenv.get("MODEL");
        this.generation = dotenv.get("GENERATION");
    }

    private void clickOptionByText(WebDriver driver, WebDriverWait wait, String text) {
        By option = By.xpath( CAR_NAME_MENU_PATH + text + "']");
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

    public void open(String link) {
        driver.get(link);
    }

    public void selectCarsCategory() {
        clickOptionByCSS(driver, wait, CATEGORIES_BUTTON_PATH);
        clickOptionByCSS(driver, wait, TRANSPORTATION_BUTTON_PATH);
        clickOptionByCSS(driver, wait, CAR_CATEGORY_BUTTON);
    }

    public void applyFilters() {
        clickOptionByCSS(driver, wait, SHOW_ALL_BUTTON);
        clickOptionByCSS(driver, wait, SKIP_BUTTON);
        clickOptionByText(driver, wait, car);
        clickOptionByText(driver, wait, model);
        clickOptionByText(driver, wait, generation);
        clickOptionById(driver, wait, APPLY_FILTERS_BUTTON);
    }

    public List<String> collectCarLinks() {
        List<String> carLinks = new ArrayList<>();

        while (true) {
            WebElement adList = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector(CAR_LISTING_PATH)
            ));

            List<WebElement> carLinksElements = adList.findElements(
                    By.cssSelector(CAR_ELEMENT_PATH)
            );

            for (WebElement linkElement : carLinksElements) {
                carLinks.add(linkElement.getAttribute(HREF_ATTRIBUTE));
            }

            List<WebElement> nextButtons = driver.findElements(By.cssSelector(NEXT_BUTTON_PATH));

            if (nextButtons.isEmpty() || !nextButtons.get(0).isEnabled()) {
                break;
            }

            // Click next and wait for the page to change
            WebElement firstCar = carLinksElements.get(0); // store the first element to wait for staleness
            WebElement next = wait.until(ExpectedConditions.elementToBeClickable(nextButtons.get(0)));
            SeleniumUtils.safeClick(driver, next);

            // Wait until the first car becomes stale (page changed)
            wait.until(ExpectedConditions.stalenessOf(firstCar));
        }

        return carLinks;
    }

}
