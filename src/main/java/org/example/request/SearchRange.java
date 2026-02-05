package org.example.request;

import java.util.Map;

public class SearchRange {
    private final int min;
    private final int max;

    public SearchRange(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public Map<String, Object> toMap() {
        return Map.of(
                "min", min,
                "max", max
        );
    }
}