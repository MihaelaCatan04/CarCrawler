package org.example.request;

import java.util.List;
import java.util.Map;

public class SearchFilter {
    private final int filterId;
    private final List<SearchFeature> features;

    public SearchFilter(int filterId, List<SearchFeature> features) {
        this.filterId = filterId;
        this.features = features;
    }

    public int getFilterId() {
        return filterId;
    }

    public List<SearchFeature> getFeatures() {
        return features;
    }

    public Map<String, Object> toMap() {
        return Map.of(
                "filterId", filterId,
                "features", features.stream().map(SearchFeature::toMap).toList()
        );
    }
}