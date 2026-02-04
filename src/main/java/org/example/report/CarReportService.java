package org.example.report;

import org.example.car_model.CarStatistics;

public class CarReportService {
    public static String generateReport(CarStatistics stats) {
        if (stats.lowestPriceLink() == null || stats.lowestPriceLink().isBlank())
        {
            return "No cars available for sale with these specifications.";
        } else {
            return """
                    Lowest price: %s €
                    Lowest link: %s
                    Highest price: %s €
                    Highest link: %s
                    Average price: %s €
                    """.formatted(
                    stats.lowestPrice(),
                    stats.lowestPriceLink(),
                    stats.highestPrice(),
                    stats.highestPriceLink(),
                    stats.averagePrice()
            );
        }
    }
}
