package org.example.car_model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Category(
        int id,
        I18NTr title,
        Category parent,
        @JsonProperty("__typename")
        String typeName
) {
}
