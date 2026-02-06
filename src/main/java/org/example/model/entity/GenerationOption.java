package org.example.model.entity;

public class GenerationOption {
    private final int id;
    private final String title;

    public GenerationOption(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
