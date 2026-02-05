package org.example.car_model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Account(
        String id,
        String login,
        String avatar,
        String createdDate,
        Business business,
        Verification verification,
        @JsonProperty("__typename")
        String typeName
) {
}
