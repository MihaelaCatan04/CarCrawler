package org.example.model;

public record OptionValue(
        Abbreviations abbreviations,
        String translated,
        int value
) {
}
