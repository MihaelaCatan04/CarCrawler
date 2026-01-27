package org.example;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;

public class CarCsvProcessor {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String ENV_CAR = "CAR";
    private static final String ENV_MODEL = "MODEL";
    private static final String ENV_GENERATION = "GENERATION";
    private static final String ENV_YEAR_MIN = "YEAR_MIN";
    private static final String ENV_YEAR_MAX = "YEAR_MAX";
    private static final String ENV_MIN_MILEAGE = "MIN_MILEAGE";
    private static final String ENV_MAX_MILEAGE = "MAX_MILEAGE";
    private static final String NOT_NUMBER_REGEX = "[^0-9]";
    private static final String SPACE_REMOVER = "";
    private static final Logger logger = LoggerFactory.getLogger(CarCsvProcessor.class);

    public List<Car> readFile(String filename) throws IOException {
        Reader csv = new FileReader(filename);

        CSVParser parser = CSVFormat.DEFAULT
                .builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .setTrim(true)
                .build()
                .parse(csv);

        List<Car> cars = new ArrayList<>();
        for (CSVRecord record : parser) {
            Car car = new Car(record);
            cars.add(car);
        }

        return cars;
    }


    public static Integer safeParseInt(String text) {
        if (text == null || text.isBlank()) {
            logger.debug("safeParseInt: input is null or blank");
            return null;
        }
        try {
            return Integer.parseInt(text.replaceAll(NOT_NUMBER_REGEX, SPACE_REMOVER));
        } catch (NumberFormatException e) {
            logger.warn("safeParseInt: failed to parse '{}' as integer", text);
            return null;
        }
    }

    public List<Car> filterCars(List<Car> carsToBeFiltered) {
        String name = dotenv.get(ENV_CAR);
        String model = dotenv.get(ENV_MODEL);
        String generation = dotenv.get(ENV_GENERATION);
        int minYear = Integer.parseInt(dotenv.get(ENV_YEAR_MIN));
        int maxYear = Integer.parseInt(dotenv.get(ENV_YEAR_MAX));
        int minMileage = Integer.parseInt(dotenv.get(ENV_MIN_MILEAGE));
        int maxMileage = Integer.parseInt(dotenv.get(ENV_MAX_MILEAGE));

        return carsToBeFiltered
                .stream()
                .filter(car ->
                        car.name() != null && car.name().equals(name) &&
                                car.model() != null && car.model().equals(model) &&
                                car.generation() != null && car.generation().equals(generation) &&
                                car.year() != null && car.year() >= minYear && car.year() <= maxYear &&
                                car.mileage() != null && car.mileage() >= minMileage && car.mileage() <= maxMileage
                )
                .toList();
    }
}
