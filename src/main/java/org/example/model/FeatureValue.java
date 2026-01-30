package org.example.model;

public record FeatureValue<T>(
        int id,
        String type,
        T value,
        String __typename
) {
}
