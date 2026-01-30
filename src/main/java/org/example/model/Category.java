package org.example.model;

public record Category(
        int id,
        I18NTr title,
        Category parent,
        String __typename
) {
}
