package org.example.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Category(
        int id,
        I18NTr title,
        Category parent,
        @JsonProperty("__typename")
        String typeName
) {
}
