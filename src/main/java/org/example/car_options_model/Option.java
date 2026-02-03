package org.example.car_options_model;

public record Option(
        int id,
        I18NTr title,
        Integer parentId,
        boolean hasChildren,
        boolean isTop,
        I18NTr seoAlias,
        boolean presentInDynamicFilters,
        boolean checked,
        Feature feature
) {
}
