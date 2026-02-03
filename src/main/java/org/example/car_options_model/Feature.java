package org.example.car_options_model;

import org.example.car_model.I18NTr;

import java.util.List;

public record Feature(
        int id,
        String fid,
        String type,
        Integer parentId,
        Integer childId,
        I18NTr title,
        String seoAlias,
        boolean presentInDynamicFilters,
        List<Option> options
) {
}
