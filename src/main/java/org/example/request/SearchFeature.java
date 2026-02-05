package org.example.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchFeature {
    private final int featureId;
    private final List<Integer> optionIds;
    private final SearchRange range;
    private final String unit;

    private SearchFeature(int featureId, List<Integer> optionIds, SearchRange range, String unit) {
        this.featureId = featureId;
        this.optionIds = optionIds;
        this.range = range;
        this.unit = unit;
    }

    public static SearchFeature withOptionIds(int featureId, List<Integer> optionIds) {
        return new SearchFeature(featureId, optionIds, null, null);
    }

    public static SearchFeature withRange(int featureId, SearchRange range) {
        return new SearchFeature(featureId, null, range, null);
    }

    public static SearchFeature withRangeAndUnit(int featureId, SearchRange range, String unit) {
        return new SearchFeature(featureId, null, range, unit);
    }

    public int getFeatureId() {
        return featureId;
    }

    public List<Integer> getOptionIds() {
        return optionIds;
    }

    public SearchRange getRange() {
        return range;
    }

    public String getUnit() {
        return unit;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("featureId", featureId);
        if (optionIds != null) {
            map.put("optionIds", optionIds);
        }
        if (range != null) {
            map.put("range", range.toMap());
        }
        if (unit != null) {
            map.put("unit", unit);
        }
        return map;
    }
}