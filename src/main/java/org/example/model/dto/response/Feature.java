package org.example.model.dto.response;

import org.example.model.entity.I18NTr;

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
