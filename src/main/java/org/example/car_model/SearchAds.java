package org.example.car_model;

import java.util.List;

public record SearchAds(
        List<Advert> ads,
        int count,
        long reseted,
        String __typename
) {
}
