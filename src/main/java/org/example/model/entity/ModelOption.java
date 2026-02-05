package org.example.model.entity;

import java.util.ArrayList;
import java.util.List;

public class ModelOption {
    private final int id;
    private final String title;
    private final List<GenerationOption> generations = new ArrayList<>();

    public ModelOption(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public List<GenerationOption> getGenerations() {
        return generations;
    }

    public void addGeneration(GenerationOption gen) {
        generations.add(gen);
    }
}
