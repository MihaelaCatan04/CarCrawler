package org.example.scraper;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {
    private static final String DISABLE_AUTOMATION_CONTROLLED = "--disable-blink-features=AutomationControlled";
    private static final String START_MAXIMIZED = "--start-maximized";
    private static final String DISABLE_INFOBARS = "--disable-infobars";
    private static final String DISABLE_DEV_SHM_USAGE = "--disable-dev-shm-usage";
    private static final String NO_SANDBOX = "--no-sandbox";
    private static final String HEADLESS = "--headless=new";
    private static final String USER_AGENT = "user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                    "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0 Safari/537.36";
    private static final String WINDOW_SIZE = "--window-size=1920,1080";


    public static WebDriver createDriver() {

        ChromeOptions options = new ChromeOptions();
        options.addArguments(HEADLESS);
        options.addArguments(DISABLE_AUTOMATION_CONTROLLED);
        options.addArguments(START_MAXIMIZED);
        options.addArguments(DISABLE_INFOBARS);
        options.addArguments(DISABLE_DEV_SHM_USAGE);
        options.addArguments(NO_SANDBOX);
        options.addArguments(USER_AGENT);
        options.addArguments(WINDOW_SIZE);


        return new ChromeDriver(options);
    }
}
