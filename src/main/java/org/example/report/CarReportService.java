package org.example.report;

import org.example.csv.CarCsvWriter;
import org.example.model.Car;
import org.example.model.CarStatistics;
import org.example.parser.CarCsvProcessor;
import org.example.scraper.CarScrapingService;
import org.example.statistics.CarStatisticsCalculator;

import java.io.IOException;
import java.util.List;

public class CarReportService {
    private static final String URL = "https://999.md/ro";
    private static final int MIN_CAR_NUMBER = 200;
    private static final String RESULT_CSV = "src/main/resources/cars_data.csv";

    public String generateReport() throws IOException {
        CarScrapingService service = new CarScrapingService();

        List<String> links = service.collectCarLinks(URL);
        List<Car> cars = service.scrapeCars(links.subList(0, Math.min(MIN_CAR_NUMBER, links.size())));

        new CarCsvWriter().write(cars, RESULT_CSV);

        CarCsvProcessor processor = new CarCsvProcessor();
        List<Car> loadedCars = processor.readFile(RESULT_CSV);
        List<Car> filteredCars = processor.filterCars(loadedCars);
        CarStatisticsCalculator calculator = new CarStatisticsCalculator();
        CarStatistics stats = calculator.calculate(filteredCars);

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
