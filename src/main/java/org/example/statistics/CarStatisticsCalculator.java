package org.example.statistics;

import org.example.car_model.Advert;
import org.example.car_model.CarStatistics;

import java.util.List;
import java.util.Objects;

public class CarStatisticsCalculator {
    private static final String URL = "https://999.md/";
    private int lowestPrice = Integer.MAX_VALUE;
    private int highestPrice = 0;
    private int averagePrice = 0;
    private String lowestPriceLink;
    private String highestPriceLink;
    private static final String UNIT_MDL = "UNIT_MDL";
    private static final String UNIT_EUR = "UNIT_EUR";
    private static final String UNIT_USD = "UNIT_USD";

    private int convertPrice(int price, String priceMeasurement) {
        if (priceMeasurement.equals(UNIT_MDL)) {
            price = (int) Math.round(price * 0.05);
        } else if (priceMeasurement.equals(UNIT_USD)) {
            price = (int) Math.round(price * 0.85);
        }
        return price;
    }

    public CarStatistics calculate(List<Advert> cars) {
        if (cars == null || cars.isEmpty()) {
            return new CarStatistics(0, null, 0, null, 0);
        }

        int sum = 0;
        int count = 0;

        for (Advert car : cars) {
            int price = car.price().value().value();
            String priceMeasurement = car.price().value().unit();

            if (!Objects.equals(priceMeasurement, UNIT_EUR)) {
                price = convertPrice(price, priceMeasurement);
            }

            if (price < lowestPrice) {
                lowestPrice = price;
                lowestPriceLink = URL + car.id();
            }

            if (price > highestPrice) {
                highestPrice = price;
                highestPriceLink = URL + car.id();
            }

            sum += price;
            count++;
        }

        averagePrice = count == 0 ? 0 : sum / count;

        return new CarStatistics(
                lowestPrice == Integer.MAX_VALUE ? 0 : lowestPrice,
                lowestPriceLink,
                highestPrice,
                highestPriceLink,
                averagePrice
        );
    }
}
