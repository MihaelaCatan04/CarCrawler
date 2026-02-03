package org.example.car_model;

public record Account(
        String id,
        String login,
        String avatar,
        String createdDate,
        Business business,
        Verification verification,
        String __typename
) {
}
