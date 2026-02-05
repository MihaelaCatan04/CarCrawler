package org.example.model.dto.response;

public record Option(
        int id,
        OptionI18NTr title,
        Integer parentId,
        boolean hasChildren,
        boolean isTop,
        OptionI18NTr seoAlias,
        boolean presentInDynamicFilters,
        boolean checked,
        Feature feature
) {
}
