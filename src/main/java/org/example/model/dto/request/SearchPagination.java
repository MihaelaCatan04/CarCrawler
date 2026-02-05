package org.example.model.dto.request;

import java.util.Map;

public class SearchPagination {
    private final int limit;
    private final int skip;

    public SearchPagination(int limit, int skip) {
        this.limit = limit;
        this.skip = skip;
    }

    public int getLimit() {
        return limit;
    }

    public int getSkip() {
        return skip;
    }

    public Map<String, Object> toMap() {
        return Map.of(
                "limit", limit,
                "skip", skip
        );
    }
}
