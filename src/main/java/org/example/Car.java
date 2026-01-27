package org.example;

public class Car {
    private final String link;
    private final String name;
    private final String model;
    private final String generation;
    private final Integer year;
    private final Integer mileage;
    private final Integer price;

    public Car(
            String link,
            String name,
            String model,
            String generation,
            Integer year,
            Integer mileage,
            Integer price
    ) {
        this.link = link;
        this.name = name;
        this.model = model;
        this.generation = generation;
        this.year = year;
        this.mileage = mileage;
        this.price = price;
    }

    public String getLink() { return link; }
    public String getName() { return name; }
    public String getModel() { return model; }
    public String getGeneration() { return generation; }
    public Integer getYear() { return year; }
    public Integer getMileage() { return mileage; }
    public Integer getPrice() { return price; }
}
