package org.example.car_model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record SearchAds(
        List<Advert> ads,
        int count,
        long reseted,
        @JsonProperty("__typename")
        String typeName
) {
}
