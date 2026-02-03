package org.example.car_model;

public record PriceValue(
        boolean bargain,
        int down_payment,
        String measurement,
        String mode,
        String unit,
        int value
) {
}
