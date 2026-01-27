package org.example;

import org.apache.commons.csv.CSVRecord;

import static org.example.CarCsvProcessor.safeParseInt;

public record Car(String link, String name, String model, String generation,
                  Integer year, Integer mileage, Integer price) {

    public Car(CSVRecord record) {
        this(
                record.get("URL"),
                record.get("Name"),
                record.get("Model"),
                record.get("Generation"),
                safeParseInt(record.get("Year")),
                safeParseInt(record.get("Mileage")),
                safeParseInt(record.get("Price"))
        );
    }
}
