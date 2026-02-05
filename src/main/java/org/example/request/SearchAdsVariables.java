package org.example.request;

import java.util.Map;

public class SearchAdsVariables {
    private final boolean isWorkCategory;
    private final boolean includeCarsFeatures;
    private final boolean includeBody;
    private final boolean includeOwner;
    private final boolean includeBoost;
    private final String locale;
    private final AdsSearchInput input;

    public SearchAdsVariables(boolean isWorkCategory, boolean includeCarsFeatures, boolean includeBody,
                              boolean includeOwner, boolean includeBoost, String locale, AdsSearchInput input) {
        this.isWorkCategory = isWorkCategory;
        this.includeCarsFeatures = includeCarsFeatures;
        this.includeBody = includeBody;
        this.includeOwner = includeOwner;
        this.includeBoost = includeBoost;
        this.locale = locale;
        this.input = input;
    }

    public boolean isWorkCategory() {
        return isWorkCategory;
    }

    public boolean isIncludeCarsFeatures() {
        return includeCarsFeatures;
    }

    public boolean isIncludeBody() {
        return includeBody;
    }

    public boolean isIncludeOwner() {
        return includeOwner;
    }

    public boolean isIncludeBoost() {
        return includeBoost;
    }

    public String getLocale() {
        return locale;
    }

    public AdsSearchInput getInput() {
        return input;
    }

    public Map<String, Object> toMap() {
        return Map.of(
                "isWorkCategory", isWorkCategory,
                "includeCarsFeatures", includeCarsFeatures,
                "includeBody", includeBody,
                "includeOwner", includeOwner,
                "includeBoost", includeBoost,
                "locale", locale,
                "input", input.toMap()
        );
    }
}