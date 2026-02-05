package org.example.car_model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Business(
        String plan,
        String id,
        @JsonProperty("__typename")
        String typeName
) {
}
