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
                        car.getLink() != null ? car.getLink() : "",
                        car.getName() != null ? car.getName() : "",
                        car.getModel() != null ? car.getModel() : "",
                        car.getGeneration() != null ? car.getGeneration() : "",
                        car.getYear() != null ? car.getYear() : "",
                        car.getMileage() != null ? car.getMileage() : "",
                        car.getPrice() != null ? car.getPrice() : ""
                ));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to write CSV", e);
        }
    }
}
