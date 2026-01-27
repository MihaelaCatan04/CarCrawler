package org.example;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CarCsvProcessor {

    public List<Car> readFile(String filename) throws IOException {
        Reader csv = new FileReader(filename);

        CSVParser parser = CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withTrim()
                .parse(csv);

        List<Car> cars = new ArrayList<>();
        for (CSVRecord record : parser) {
            Car car = new Car(
                    record.get("URL"),
                    record.get("Name"),
                    record.get("Model"),
                    record.get("Generation"),
                    safeParseInt(record.get("Year")),
                    safeParseInt(record.get("Mileage")),
                    safeParseInt(record.get("Price"))
            );

            cars.add(car);
        }

        return cars;
    }

    private Integer safeParseInt(String text) {
        if (text == null || text.isBlank()) return null;
        try {
            return Integer.parseInt(text.replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public List<Car> filterCars(List<Car> carsToBeFiltered) {
        Dotenv dotenv = Dotenv.load();
        String name = dotenv.get("CAR");
        String model = dotenv.get("MODEL");
        String generation = dotenv.get("GENERATION");
        int minYear = Integer.parseInt(dotenv.get("YEAR_MIN"));
        int maxYear = Integer.parseInt(dotenv.get("YEAR_MAX"));
        int minMileage = Integer.parseInt(dotenv.get("MIN_MILEAGE"));
        int maxMileage = Integer.parseInt(dotenv.get("MAX_MILEAGE"));

        return carsToBeFiltered
                .stream()
                .filter(car ->
                        car.getName() != null && car.getName().equals(name) &&
                                car.getModel() != null && car.getModel().equals(model) &&
                                car.getGeneration() != null && car.getGeneration().equals(generation) &&
                                car.getYear() != null && car.getYear() >= minYear && car.getYear() <= maxYear &&
                                car.getMileage() != null && car.getMileage() >= minMileage && car.getMileage() <= maxMileage
                )
                .toList();
    }
}
