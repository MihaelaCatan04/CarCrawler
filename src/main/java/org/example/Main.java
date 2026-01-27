package org.example;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        CarScrapingService service = new CarScrapingService();

        List<String> links = service.collectCarLinks("https://999.md/ro");
        List<Car> cars = service.scrapeCars(links.subList(0, Math.min(200, links.size())));

        new CarCsvWriter().write(cars, "src/main/resources/cars_data.csv");

        CarCsvProcessor processor = new CarCsvProcessor();
        List<Car> loadedCars = processor.readFile("src/main/resources/cars_data.csv");
        List<Car> filteredCars = processor.filterCars(loadedCars);
        CarStatisticsCalculator calculator = new CarStatisticsCalculator();
        CarStatistics stats = calculator.calculate(filteredCars);

        System.out.println("Lowest price: " + stats.lowestPrice() + "€");
        System.out.println("Lowest link: " + stats.lowestPriceLink());
        System.out.println("Highest price: " + stats.highestPrice() + "€");
        System.out.println("Highest link: " + stats.highestPriceLink());
        System.out.println("Average price: " + stats.averagePrice() + "€");
    }
}
