package org.example.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.Thread.sleep;

public class SeleniumUtils {

    private static final Logger logger = LoggerFactory.getLogger(SeleniumUtils.class);

    // JS snippets
    private static final String JS_SCROLL_INTO_VIEW =
            "arguments[0].scrollIntoView({block: 'center'});";

    private static final String JS_CLICK =
            "arguments[0].click();";

    private static final String JS_HIDE_WEBDRIVER_FLAG =
            "Object.defineProperty(navigator, 'webdriver', {get: () => undefined});";

    private static final String JS_REACT_SAFE_INPUT =
            "let element = arguments[0];" +
                    "let text = arguments[1];" +
                    "element.focus();" +
                    "element.value = '';" +
                    "element.value = text;" +
                    "element.dispatchEvent(new Event('input', { bubbles: true, cancelable: true }));" +
                    "element.dispatchEvent(new Event('change', { bubbles: true, cancelable: true }));" +
                    "element.dispatchEvent(new KeyboardEvent('keydown', { bubbles: true, cancelable: true }));" +
                    "element.dispatchEvent(new KeyboardEvent('keyup', { bubbles: true, cancelable: true }));" +
                    "element.dispatchEvent(new KeyboardEvent('keypress', { bubbles: true, cancelable: true }));" +
                    "element.dispatchEvent(new Event('blur', { bubbles: true, cancelable: true }));" +
                    "let tracker = element._valueTracker;" +
                    "if (tracker) { tracker.setValue(''); }" +
                    "element.dispatchEvent(new Event('input', { bubbles: true }));";

    private static final String JS_DISABLE_STICKY_HEADER =
            "document.querySelectorAll('header, .sticky, .fixed').forEach(e => e.style.pointerEvents='none');";


    public static void safeClick(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(JS_SCROLL_INTO_VIEW, element);
        js.executeScript(JS_CLICK, element);

        js.executeScript(JS_DISABLE_STICKY_HEADER);
    }

    public static void safeSendKeys(WebDriver driver, WebElement element, String text) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(JS_SCROLL_INTO_VIEW, element);
            sleep(200);
            js.executeScript(JS_REACT_SAFE_INPUT, element, text);
            sleep(300);

            String actualValue = element.getAttribute("value");
        } catch (Exception e) {
            throw new RuntimeException("Failed to send keys safely via JS", e);
        }
    }

    public static void hideWebDriverFlag(WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript(JS_HIDE_WEBDRIVER_FLAG);
    }
}
