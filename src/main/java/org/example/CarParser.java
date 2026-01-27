package org.example;

public class CarParser {

    private final double USD_TO_EUR = 0.85;
    private final double MDL_TO_EUR = 0.05;
    private final String EURO_SIGN = "€";
    private final String DOLLAR_SIGN = "$";
    private final String MDL_SIGN = "MDL";
    private final String SPACE = " ";
    private final String KM_SIGN = " km";

    public Car parse(String link, CarDetailsPage page) {
        Integer year = safeParseInt(page.getYearText());
        Integer mileage = safeParseMileage(page.getMileageText());
        Integer price = parsePriceToEuroSafe(page.getPriceText());

        return new Car(
                link,
                page.getName(),
                page.getModel(),
                page.getGeneration(),
                year,
                mileage,
                price
        );
    }

    private Integer safeParseInt(String text) {
        if (text == null || text.isBlank()) return null;
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Integer safeParseMileage(String text) {
        if (text == null) return null;
        return safeParseInt(text.replace(KM_SIGN, "").replace(SPACE, ""));
    }

    private Integer parsePriceToEuroSafe(String priceText) {
        if (priceText == null || priceText.isBlank()) return null;

        boolean isLei = priceText.contains(MDL_SIGN);
        boolean isDollar = priceText.contains(DOLLAR_SIGN);

        Integer price = safeParseInt(
                priceText.replace(EURO_SIGN, "")
                        .replace(DOLLAR_SIGN, "")
                        .replace(MDL_SIGN, "")
                        .replace(SPACE, "")
        );

        if (price == null) return null;

        if (isDollar) price = (int) Math.round(price * USD_TO_EUR);
        if (isLei) price = (int) Math.round(price * MDL_TO_EUR);

        return price;
    }
}
