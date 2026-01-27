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
    private final static Dotenv dotenv = Dotenv.load();
    private final String car;
    private final String model;
    private final String generation;
    private final static String CAR_NAME_MENU_PATH = "//label[normalize-space()='";
    private final static String CATEGORIES_BUTTON_PATH = "button[data-testid='categories-toggle']";
    private final static String TRANSPORTATION_BUTTON_PATH = "li[data-url='transport']";
    private final static String CAR_CATEGORY_BUTTON = "a[data-subcategory='659']";
    private final static String SHOW_ALL_BUTTON = "button[data-testid='show_all_btn']";
    private final static String SKIP_BUTTON = "a.introjs-skipbutton";
    private final static String APPLY_FILTERS_BUTTON = "apply-filters-btn";
    private final static String CAR_LISTING_PATH = "div[data-sentry-component='AdList']";
    private final static String CAR_ELEMENT_PATH = "div.AdPhoto_wrapper__gAOIH a.AdPhoto_image__BMixw";
    private final static String HREF_ATTRIBUTE = "href";
    private final static String NEXT_BUTTON_PATH = "button.Pagination_pagination__container__buttons__wrapper__icon__next__A22Rc";
    private final static int DURATION_SECONDS = 20;
    private static final String ENV_CAR = "CAR";
    private static final String ENV_MODEL = "MODEL";
    private static final String ENV_GENERATION = "GENERATION";


    public CarSearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DURATION_SECONDS));
        this.car = dotenv.get(ENV_CAR);
        this.model = dotenv.get(ENV_MODEL);
        this.generation = dotenv.get(ENV_GENERATION);
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

            if (nextButtons.isEmpty() || !nextButtons.getFirst().isEnabled()) {
                break;
            }

            // Click next and wait for the page to change
            WebElement firstCar = carLinksElements.getFirst(); // store the first element to wait for staleness
            WebElement next = wait.until(ExpectedConditions.elementToBeClickable(nextButtons.getFirst()));
            SeleniumUtils.safeClick(driver, next);

            // Wait until the first car becomes stale (page changed)
            wait.until(ExpectedConditions.stalenessOf(firstCar));
        }

        return carLinks;
    }

}
