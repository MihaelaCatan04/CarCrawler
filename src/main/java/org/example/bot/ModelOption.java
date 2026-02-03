package org.example.bot;

import java.util.ArrayList;
import java.util.List;

public class ModelOption {
    public int id;
    public String title;
    public List<GenerationOption> generations = new ArrayList<>();

    public ModelOption(int id, String title) {
        this.id = id;
        this.title = title;
    }
}