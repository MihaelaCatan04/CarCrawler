package org.example.car_model;

import java.util.List;

public record Advert(
        String id,
        String title,

        Category subCategory,

        FeatureValue<PriceValue> price,
        FeatureValue<Integer> transportYear,
        FeatureValue<MileageValue> mileage,

        FeatureValue<List<String>> images,

        FeatureValue<OptionValue> carFuel,
        FeatureValue<OptionValue> carDrive,
        FeatureValue<OptionValue> carTransmission,

        FeatureValue<OptionValue> realEstate,

        Account owner,

        Object pricePerMeter,
        Object oldPrice,
        Object engineVolume,
        Object label,
        Object frame,
        Object animation,
        Object animationAndFrame,

        String reseted,
        String __typename
) {
}
