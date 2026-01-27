package org.example.main;

import java.io.IOException;
import java.util.List;

import org.example.csv.CarCsvWriter;
import org.example.model.Car;
import org.example.model.CarStatistics;
import org.example.parser.CarCsvProcessor;
import org.example.scraper.CarScrapingService;
import org.example.statistics.CarStatisticsCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final String URL = "https://999.md/ro";
    private static final int MIN_CAR_NUMBER = 20;
    private static final String RESULT_CSV = "src/main/resources/cars_data.csv";
    private static final String LOWEST_PRICE = "Lowest price: ";
    private static final String EURO = "€";
    private static final String LOWEST_LINK = "Lowest link: ";
    private static final String HIGHEST_PRICE = "Highest price: ";
    private static final String HIGHEST_LINK = "Highest link: ";
    private static final String AVERAGE_PRICE = "Average price: ";
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        CarScrapingService service = new CarScrapingService();

        List<String> links = service.collectCarLinks(URL);
        List<Car> cars = service.scrapeCars(links.subList(0, Math.min(MIN_CAR_NUMBER, links.size())));

        new CarCsvWriter().write(cars, RESULT_CSV);

        CarCsvProcessor processor = new CarCsvProcessor();
        List<Car> loadedCars = processor.readFile(RESULT_CSV);
        List<Car> filteredCars = processor.filterCars(loadedCars);
        CarStatisticsCalculator calculator = new CarStatisticsCalculator();
        CarStatistics stats = calculator.calculate(filteredCars);

        logger.info(LOWEST_PRICE + "{}" + EURO, stats.lowestPrice());
        logger.info(LOWEST_LINK + "{}", stats.lowestPriceLink());
        logger.info(HIGHEST_PRICE + "{}" + EURO, stats.highestPrice());
        logger.info(HIGHEST_LINK + "{}", stats.highestPriceLink());
        logger.info(AVERAGE_PRICE + "{}" + EURO, stats.averagePrice());
    }
}
