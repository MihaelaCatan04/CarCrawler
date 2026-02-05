package org.example.model.entity;

public record PriceValue(
        boolean bargain,
        int down_payment,
        String measurement,
        String mode,
        String unit,
        int value
) {
}
