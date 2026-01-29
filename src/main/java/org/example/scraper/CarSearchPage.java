package org.example.scraper;

import io.github.cdimascio.dotenv.Dotenv;
import org.example.utils.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CarSearchPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final static Dotenv dotenv = Dotenv.load();
    private final String car;
    private final String model;
    private final String generation;
    private final String minYear;
    private final String maxYear;
    private final String minMileage;
    private final String maxMileage;
    private final static String MIN_PRICE_PATH = "input[value name='from_10000_1000000']";
    private final static String MAX_PRICE_PATH = "input[value name='to_1000000_10000000']";
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
    private static final Logger logger = LoggerFactory.getLogger(CarSearchPage.class);
    private final static String MIN_YEAR_PATH = "input[name='from_7_19']";
    private final static String MAX_YEAR_PATH = "input[name='to_7_19']";
    private final static String MILEAGE_PATH = "button[data-testid='filter_type_range_1081']";
    private final static String MIN_MILEAGE_PATH = "input[name='from_1081_104']";
    private final static String MAX_MILEAGE_PATH = "input[name='to_1081_104']";
    private final static String ENV_MIN_YEAR = "YEAR_MIN";
    private final static String ENV_MAX_YEAR = "YEAR_MAX";
    private final static String ENV_MIN_MILEAGE = "MIN_MILEAGE";
    private final static String ENV_MAX_MILEAGE = "MAX_MILEAGE";
    public CarSearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DURATION_SECONDS));
        this.car = dotenv.get(ENV_CAR);
        this.model = dotenv.get(ENV_MODEL);
        this.generation = dotenv.get(ENV_GENERATION);
        this.minYear = dotenv.get(ENV_MIN_YEAR);
        this.maxYear = dotenv.get(ENV_MAX_YEAR);
        this.minMileage = dotenv.get(ENV_MIN_MILEAGE);
        this.maxMileage = dotenv.get(ENV_MAX_MILEAGE);
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

    private void clickOptionByCSSIfExists(WebDriver driver, WebDriverWait wait, String cssSelector) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(cssSelector)));
            SeleniumUtils.safeClick(driver, element);
        } catch (Exception e) {
            logger.warn("Optional element not found or clickable: " + cssSelector);
        }
    }


    private void enterOptionByCSS(WebDriver driver, WebDriverWait wait, String css, String value) {
        try {
            By locator = By.cssSelector(css);

            WebElement element = wait.until(
                    ExpectedConditions.presenceOfElementLocated(locator)
            );

            SeleniumUtils.safeSendKeys(driver, element, value);

        } catch (Exception e) {
            logger.error("Failed to enter value for input: " + css, e);
        }
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
        clickOptionByCSSIfExists(driver, wait, SKIP_BUTTON);
        clickOptionByText(driver, wait, car);
        clickOptionByText(driver, wait, model);
        clickOptionByText(driver, wait, generation);
        enterOptionByCSS(driver, wait, MIN_YEAR_PATH, minYear);
        enterOptionByCSS(driver, wait, MAX_YEAR_PATH, maxYear);
        clickOptionByCSS(driver, wait, MILEAGE_PATH);

        wait.until(d -> {
            WebElement min = d.findElement(By.cssSelector(MIN_MILEAGE_PATH));
            WebElement max = d.findElement(By.cssSelector(MAX_MILEAGE_PATH));
            return min.isDisplayed() && min.getSize().getHeight() > 0 &&
                    max.isDisplayed() && max.getSize().getHeight() > 0;
        });

        enterOptionByCSS(driver, wait, MIN_MILEAGE_PATH, minMileage);
        enterOptionByCSS(driver, wait, MAX_MILEAGE_PATH, maxMileage);
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
