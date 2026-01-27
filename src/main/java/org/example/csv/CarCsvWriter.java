package org.example.csv;

import org.example.model.Car;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CarCsvWriter {

    private static final String CSV_HEADER =
            "\"URL\",\"Name\",\"Model\",\"Generation\",\"Year\",\"Mileage\",\"Price\"";

    public void write(List<Car> cars, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(CSV_HEADER);
            writer.newLine();

            for (Car car : cars) {
                writer.write(car.toCsvLine());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("ERROR_WRITE_CSV", e);
        }
    }
}
