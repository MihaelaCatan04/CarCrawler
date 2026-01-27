package org.example;

import org.apache.commons.csv.CSVRecord;

public record Car(String link, String name, String model, String generation,
                  Integer year, Integer mileage, Integer price) {
    private static final String EMPTY = "";
    private static final String CSV_ROW_FORMAT =
            "\"%s\",\"%s\",\"%s\",\"%s\",%s,%s,%s";
    private static final String CSV_URL = "URL";
    private static final String CSV_NAME = "Name";
    private static final String CSV_MODEL = "Model";
    private static final String CSV_GENERATION = "Generation";
    private static final String CSV_YEAR = "Year";
    private static final String CSV_MILEAGE = "Mileage";
    private static final String CSV_PRICE = "Price";

    public Car(CSVRecord record) {
        this(
                record.get(CSV_URL),
                record.get(CSV_NAME),
                record.get(CSV_MODEL),
                record.get(CSV_GENERATION),
                CarCsvProcessor.safeParseInt(record.get(CSV_YEAR)),
                CarCsvProcessor.safeParseInt(record.get(CSV_MILEAGE)),
                CarCsvProcessor.safeParseInt(record.get(CSV_PRICE))
        );
    }

    public Car (String link, CarDetailsPage page) {
        this(
                link,
                page.getName(),
                page.getModel(),
                page.getGeneration(),
                CarParser.safeParseInt(page.getYearText()),
                CarParser.safeParseMileage(page.getMileageText()),
                CarParser.parsePriceToEuroSafe(page.getPriceText())
        );
    }

    public String toCsvLine() {
        return String.format(
                CSV_ROW_FORMAT,
                nullToEmpty(link),
                nullToEmpty(name),
                nullToEmpty(model),
                nullToEmpty(generation),
                nullToEmpty(year),
                nullToEmpty(mileage),
                nullToEmpty(price)
        );
    }

    private static String nullToEmpty(Object value) {
        return value == null ? EMPTY : value.toString();
    }
}
