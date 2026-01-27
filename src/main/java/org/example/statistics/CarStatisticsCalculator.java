package org.example.statistics;

import org.example.model.Car;
import org.example.model.CarStatistics;

import java.util.List;

public class CarStatisticsCalculator {
    private int lowestPrice = Integer.MAX_VALUE;
    private int highestPrice = 0;
    private int averagePrice = 0;
    private String lowestPriceLink;
    private String highestPriceLink;

    public CarStatistics calculate(List<Car> cars) {
        if (cars == null || cars.isEmpty()) {
            return new CarStatistics(0, null, 0, null, 0);
        }

        int sum = 0;
        int count = 0;

        for (Car car : cars) {
            Integer price = car.price();
            if (price == null) continue;

            if (price < lowestPrice) {
                lowestPrice = price;
                lowestPriceLink = car.link();
            }

            if (price > highestPrice) {
                highestPrice = price;
                highestPriceLink = car.link();
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
