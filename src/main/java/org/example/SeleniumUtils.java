package org.example;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SeleniumUtils {

    private static final String JS_SCROLL_INTO_VIEW =
            "arguments[0].scrollIntoView(true);";

    private static final String JS_CLICK =
            "arguments[0].click();";

    private static final String JS_HIDE_WEBDRIVER_FLAG =
            "Object.defineProperty(navigator, 'webdriver', {get: () => undefined})";

    public static void safeClick(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(JS_SCROLL_INTO_VIEW, element);
        js.executeScript(JS_CLICK, element);
    }

    public static void hideWebDriverFlag(WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript(JS_HIDE_WEBDRIVER_FLAG);
    }
}
