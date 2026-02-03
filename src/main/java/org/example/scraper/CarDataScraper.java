package org.example.scraper;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class CarDataScraper {

    private static final Logger log = LoggerFactory.getLogger(CarDataScraper.class);

    private static final String URL = "https://999.md/ro/list/transport/cars";
    private static final String SHOW_ALL_BTN = "button[data-testid='show_all_btn']";
    private static final String POPUP_CLOSE = "a.introjs-skipbutton";

    private static final String BRAND_INPUT = "input[data-testid$='level1']";
    private static final String ITEM_CONTAINER = "div.styles_checkbox__ONbig";

    private static final String BRAND_SECTION = "div.styles_expanded__T0p0R";

    private static final String FILTER_BUTTON = "button[data-testid='filter_type_options_1']";

    public static List<CarBrand> scrapeBrands() {

        List<CarBrand> brands = new ArrayList<>();

        try (Playwright playwright = Playwright.create()) {

            Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions()
                            .setHeadless(true)
                            .setSlowMo(50)
            );

            Page page = browser.newPage();
            page.navigate(URL);
            page.waitForLoadState(LoadState.NETWORKIDLE);

            clickIfExists(page, SHOW_ALL_BTN);
            clickIfExists(page, POPUP_CLOSE);

            Locator brandSection = page.locator(BRAND_SECTION)
                    .filter(new Locator.FilterOptions()
                            .setHas(page.locator(FILTER_BUTTON)));

            if (brandSection.count() == 0) {
                log.error("Could not find the Brand (Marcă) section!");
                return brands;
            }

            Locator brandContainers = brandSection.locator(ITEM_CONTAINER).filter(
                    new Locator.FilterOptions().setHas(page.locator(BRAND_INPUT))
            );

            int count = brandContainers.count();

            for (int i = 0; i < count; i++) {
                Locator container = brandContainers.nth(i);

                String idStr = container.locator(BRAND_INPUT).first().getAttribute("value");
                String name = container.locator("label").first().textContent().trim();

                int id = parseId(idStr);

                brands.add(new CarBrand(name, id));

            }

            browser.close();

        } catch (Exception e) {
            log.error("Scraping failed", e);
        }

        return brands;
    }

    private static int parseId(String idStr) {
        try {
            return Integer.parseInt(idStr);
        } catch (Exception e) {
            return -1;
        }
    }

    private static void clickIfExists(Page page, String selector) {
        Locator loc = page.locator(selector);
        if (loc.count() > 0 && loc.first().isVisible()) {
            loc.first().click();
            page.waitForTimeout(500);
        }
    }
}
