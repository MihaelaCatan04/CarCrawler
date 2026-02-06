package org.example.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public record I18NTr(
        String translated,
        @JsonProperty("__typename")
        String typeName
) {
}
