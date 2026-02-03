package org.example.car_model;

public record FeatureValue<T>(
        int id,
        String type,
        T value,
        String __typename
) {
}
