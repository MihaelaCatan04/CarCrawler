package org.example;

import org.example.Car;
import org.example.CarDetailsPage;

public class CarParser {

    private final double USD_TO_EUR = 0.85;
    private final double MDL_TO_EUR = 0.05;

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
    }private Integer safeParseInt(String text) {
        if (text == null || text.isBlank()) return null;
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Integer safeParseMileage(String text) {
        if (text == null) return null;
        return safeParseInt(text.replace(" km", "").replace(" ", ""));
    }

    private Integer parsePriceToEuroSafe(String priceText) {
        if (priceText == null || priceText.isBlank()) return null;

        boolean isLei = priceText.contains("MDL");
        boolean isDollar = priceText.contains("$");

        Integer price = safeParseInt(
                priceText.replace("€", "")
                        .replace("$", "")
                        .replace("MDL", "")
                        .replace(" ", "")
        );

        if (price == null) return null;

        if (isDollar) price = (int) Math.round(price * USD_TO_EUR);
        if (isLei) price = (int) Math.round(price * MDL_TO_EUR);

        return price;
    }
}
