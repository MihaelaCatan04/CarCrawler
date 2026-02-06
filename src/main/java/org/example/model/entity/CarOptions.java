package org.example.model.entity;

import java.util.ArrayList;
import java.util.List;

public class CarOptions {
    private final List<ModelOption> models = new ArrayList<>();

    public List<ModelOption> getModels() {
        return models;
    }

    public void addModel(ModelOption model) {
        models.add(model);
    }
}
