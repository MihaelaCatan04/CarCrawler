package org.example;

public class CarParser {

    private final static double USD_TO_EUR = 0.85;
    private final static double MDL_TO_EUR = 0.05;
    private final static String EURO_SIGN = "€";
    private final static String DOLLAR_SIGN = "$";
    private final static String MDL_SIGN = "MDL";
    private final static String SPACE = " ";
    private final static String KM_SIGN = " km";
    private static final String SPACE_REMOVER = "";

    public Car parse(String link, CarDetailsPage page) {
        return new Car(link, page);

    }

    public static Integer safeParseInt(String text) {
        if (text == null || text.isBlank()) return null;
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Integer safeParseMileage(String text) {
        if (text == null) return null;
        return safeParseInt(text.replace(KM_SIGN, SPACE_REMOVER).replace(SPACE, SPACE_REMOVER));
    }

    public static Integer parsePriceToEuroSafe(String priceText) {
        if (priceText == null || priceText.isBlank()) return null;

        boolean isLei = priceText.contains(MDL_SIGN);
        boolean isDollar = priceText.contains(DOLLAR_SIGN);

        Integer price = safeParseInt(
                priceText.replace(EURO_SIGN, SPACE_REMOVER)
                        .replace(DOLLAR_SIGN, SPACE_REMOVER)
                        .replace(MDL_SIGN, SPACE_REMOVER)
                        .replace(SPACE, SPACE_REMOVER)
        );

        if (price == null) return null;

        if (isDollar) price = (int) Math.round(price * USD_TO_EUR);
        if (isLei) price = (int) Math.round(price * MDL_TO_EUR);

        return price;
    }
}
