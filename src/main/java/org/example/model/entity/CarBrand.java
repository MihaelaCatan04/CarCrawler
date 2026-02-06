package org.example.model.entity;

public class CarBrand {
    private final String name;
    private final int id;

    public CarBrand(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
