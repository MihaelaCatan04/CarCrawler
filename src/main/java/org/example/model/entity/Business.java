package org.example.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Business(
        String plan,
        String id,
        @JsonProperty("__typename")
        String typeName
) {
}
