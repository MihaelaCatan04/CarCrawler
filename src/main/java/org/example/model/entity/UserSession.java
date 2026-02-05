package org.example.model.entity;

import org.example.telegram.session.Step;

public class UserSession {
    public int brandId;
    public int modelId;
    public int generationId;
    public int minYear;
    public int maxYear;
    public int minMileage;
    public int maxMileage;
    public Step step = Step.BRAND;
    public CarOptions carOptions;
}
