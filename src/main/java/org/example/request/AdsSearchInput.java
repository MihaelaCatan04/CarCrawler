package org.example.request;

import java.util.List;
import java.util.Map;

public class AdsSearchInput {
    private final int subCategoryId;
    private final String source;
    private final SearchPagination pagination;
    private final List<SearchFilter> filters;

    public AdsSearchInput(int subCategoryId, String source, SearchPagination pagination, List<SearchFilter> filters) {
        this.subCategoryId = subCategoryId;
        this.source = source;
        this.pagination = pagination;
        this.filters = filters;
    }

    public int getSubCategoryId() {
        return subCategoryId;
    }

    public String getSource() {
        return source;
    }

    public SearchPagination getPagination() {
        return pagination;
    }

    public List<SearchFilter> getFilters() {
        return filters;
    }

    public Map<String, Object> toMap() {
        return Map.of(
                "subCategoryId", subCategoryId,
                "source", source,
                "pagination", pagination.toMap(),
                "filters", filters.stream().map(SearchFilter::toMap).toList()
        );
    }
}