package org.example.parser;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.model.Car;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;

public class CarCsvProcessor {
    private static final String SPACE_REMOVER = "";
    private static final Logger logger = LoggerFactory.getLogger(CarCsvProcessor.class);
    private static final String NOT_NUMBER_REGEX = "[^0-9]";

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
        return carsToBeFiltered
                .stream()
                .filter(car -> car.isValid()
                )
                .toList();
    }
}
