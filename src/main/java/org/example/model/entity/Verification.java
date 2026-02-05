package org.example.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Verification(
        boolean isVerified,
        String date,
        @JsonProperty("__typename")
        String typeName
) {
}
