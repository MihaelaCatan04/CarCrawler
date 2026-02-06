package org.example.model.entity;

public record OptionValue(
        Abbreviations abbreviations,
        String translated,
        int value
) {
}
