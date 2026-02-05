package org.example.car_model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public record Verification(
        boolean isVerified,
        String date,
        @JsonProperty("__typename")
        String typeName
) {
}
