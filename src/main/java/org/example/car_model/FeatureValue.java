package org.example.car_model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FeatureValue<T>(
        int id,
        String type,
        T value,
        @JsonProperty("__typename")
        String typeName
) {
}
