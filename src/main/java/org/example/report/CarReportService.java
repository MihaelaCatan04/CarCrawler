package org.example.report;

import org.example.model.CarStatistics;

public class CarReportService {
    public static String generateReport(CarStatistics stats) {
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
