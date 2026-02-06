package org.example.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.model.entity.Advert;

import java.util.List;

public record SearchAds(
        List<Advert> ads,
        int count,
        long reseted,
        @JsonProperty("__typename")
        String typeName
) {
}
