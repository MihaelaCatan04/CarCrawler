package org.example.request;

public class FeatureRequestVariables {
    private final FeatureRequestInput input;

    public FeatureRequestVariables(FeatureRequestInput input) {
        this.input = input;
    }

    public FeatureRequestInput getInput() {
        return input;
    }
}