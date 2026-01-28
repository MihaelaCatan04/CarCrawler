package org.example.model;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.commons.csv.CSVRecord;
import org.example.parser.CarCsvProcessor;
import org.example.scraper.CarDetailsPage;
import org.example.parser.CarParser;

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
    private static final Dotenv dotenv = Dotenv.load();
    private static final String ENV_CAR = "CAR";
    private static final String ENV_MODEL = "MODEL";
    private static final String ENV_GENERATION = "GENERATION";
    private static final String ENV_YEAR_MIN = "YEAR_MIN";
    private static final String ENV_YEAR_MAX = "YEAR_MAX";
    private static final String ENV_MIN_MILEAGE = "MIN_MILEAGE";
    private static final String ENV_MAX_MILEAGE = "MAX_MILEAGE";

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

    public boolean isValid() {
        String name = dotenv.get(ENV_CAR);
        String model = dotenv.get(ENV_MODEL);
        String generation = dotenv.get(ENV_GENERATION);
        int minYear = Integer.parseInt(dotenv.get(ENV_YEAR_MIN));
        int maxYear = Integer.parseInt(dotenv.get(ENV_YEAR_MAX));
        int minMileage = Integer.parseInt(dotenv.get(ENV_MIN_MILEAGE));
        int maxMileage = Integer.parseInt(dotenv.get(ENV_MAX_MILEAGE));
        return this.name != null && this.name.equals(name) &&
                this.model != null && this.model.equals(model) &&
                this.generation != null && this.generation.equals(generation) &&
                this.year != null && this.year >= minYear && this.year <= maxYear &&
                this.mileage != null && this.mileage >= minMileage && this.mileage <= maxMileage;
    }
}
