package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CarCsvWriter {

    public void write(List<Car> cars, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("\"URL\",\"Name\",\"Model\",\"Generation\",\"Year\",\"Mileage\",\"Price\"");
            writer.newLine();

            for (Car car : cars) {
                writer.write(String.format(
                        "\"%s\",\"%s\",\"%s\",\"%s\",%s,%s,%s",
                        car.link() != null ? car.link() : "",
                        car.name() != null ? car.name() : "",
                        car.model() != null ? car.model() : "",
                        car.generation() != null ? car.generation() : "",
                        car.year() != null ? car.year() : "",
                        car.mileage() != null ? car.mileage() : "",
                        car.price() != null ? car.price() : ""
                ));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to write CSV", e);
        }
    }
}
