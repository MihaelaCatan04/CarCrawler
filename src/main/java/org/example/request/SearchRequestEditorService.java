package org.example.request;

import org.example.bot.UserSession;

import java.util.List;
import java.util.Map;

public class SearchRequestEditorService {

    private static final String QUERY = """
            query SearchAds($input: Ads_SearchInput!, $isWorkCategory: Boolean = false, $includeCarsFeatures: Boolean = false, $includeBody: Boolean = false, $includeOwner: Boolean = false, $includeBoost: Boolean = false, $locale: Common_Locale) {
              searchAds(input: $input) {
                ads {
                  ...AdsSearchFragment
                  __typename
                }
                count
                reseted
                __typename
              }
            }
            
            fragment AdsSearchFragment on Advert {
              ...AdListFragment
              ...WorkCategoryFeatures @include(if: $isWorkCategory)
              reseted(
                input: {format: "2 Jan. 2006, 15:04", locale: $locale, timezone: "Europe/Chisinau", getDiff: false}
              )
              __typename
            }
            
            fragment AdListFragment on Advert {
              id
              title
              subCategory {
                ...CategoryAdFragment
                __typename
              }
              ...PriceAndImages
              ...CarsFeatures @include(if: $includeCarsFeatures)
              ...AdvertOwner @include(if: $includeOwner)
              transportYear: feature(id: 19) {
                ...FeatureValueFragment
                __typename
              }
              realEstate: feature(id: 795) {
                ...FeatureValueFragment
                __typename
              }
              body: feature(id: 13) @include(if: $includeBody) {
                ...FeatureValueFragment
                __typename
              }
              ...AdvertBooster @include(if: $includeBoost)
              label: displayProduct(alias: LABEL) {
                ... on DisplayLabel {
                  enable
                  ...DisplayLabelFragment
                  __typename
                }
                __typename
              }
              frame: displayProduct(alias: FRAME) {
                ... on DisplayFrame {
                  enable
                  __typename
                }
                __typename
              }
              animation: displayProduct(alias: ANIMATION) {
                ... on DisplayAnimation {
                  enable
                  __typename
                }
                __typename
              }
              animationAndFrame: displayProduct(alias: ANIMATION_AND_FRAME) {
                ... on DisplayAnimationAndFrame {
                  enable
                  __typename
                }
                __typename
              }
              __typename
            }
            
            fragment CategoryAdFragment on Category {
              id
              title {
                ...TranslationFragment
                __typename
              }
              parent {
                id
                title {
                  ...TranslationFragment
                  __typename
                }
                parent {
                  id
                  title {
                    ...TranslationFragment
                    __typename
                  }
                  __typename
                }
                __typename
              }
              __typename
            }
            
            fragment TranslationFragment on I18NTr {
              translated
              __typename
            }
            
            fragment PriceAndImages on Advert {
              price: feature(id: 2) {
                ...FeatureValueFragment
                __typename
              }
              pricePerMeter: feature(id: 1385) {
                ...FeatureValueFragment
                __typename
              }
              oldPrice: feature(id: 1640) {
                ...FeatureValueFragment
                __typename
              }
              images: feature(id: 14) {
                ...FeatureValueFragment
                __typename
              }
              __typename
            }
            
            fragment FeatureValueFragment on FeatureValue {
              id
              type
              value
              __typename
            }
            
            fragment CarsFeatures on Advert {
              carFuel: feature(id: 151) {
                ...FeatureValueFragment
                __typename
              }
              carDrive: feature(id: 108) {
                ...FeatureValueFragment
                __typename
              }
              carTransmission: feature(id: 101) {
                ...FeatureValueFragment
                __typename
              }
              mileage: feature(id: 104) {
                ...FeatureValueFragment
                __typename
              }
              engineVolume: feature(id: 103) {
                ...FeatureValueFragment
                __typename
              }
              __typename
            }
            
            fragment AdvertOwner on Advert {
              owner {
                ...AccountFragment
                __typename
              }
              __typename
            }
            
            fragment AccountFragment on Account {
              id
              login
              avatar
              createdDate
              business {
                plan
                id
                __typename
              }
              verification {
                isVerified
                date(input: {timezone: "Europe/Chisinau", getDiff: false})
                __typename
              }
              __typename
            }
            
            fragment AdvertBooster on Advert {
              booster: product(alias: BOOSTER) {
                enable
                __typename
              }
              __typename
            }
            
            fragment DisplayLabelFragment on DisplayLabel {
              title
              color {
                ...ColorFragment
                __typename
              }
              gradient {
                ...GradientFragment
                __typename
              }
              __typename
            }
            
            fragment ColorFragment on Common_Color {
              r
              g
              b
              a
              __typename
            }
            
            fragment GradientFragment on Gradient {
              from {
                ...ColorFragment
                __typename
              }
              to {
                ...ColorFragment
                __typename
              }
              position
              rotation
              __typename
            }
            
            fragment WorkCategoryFeatures on Advert {
              salary: feature(id: 266) {
                ...FeatureValueFragment
                __typename
              }
              workSchedule: feature(id: 260) {
                ...FeatureValueFragment
                __typename
              }
              workExperience: feature(id: 263) {
                ...FeatureValueFragment
                __typename
              }
              education: feature(id: 261) {
                ...FeatureValueFragment
                __typename
              }
              __typename
            }
            """;

    private Map<String, Object> modifyVariables(UserSession s) {

        return Map.of(
                "isWorkCategory", false,
                "includeCarsFeatures", true,
                "includeBody", false,
                "includeOwner", true,
                "includeBoost", false,
                "locale", "ro_RO",
                "input", Map.of(
                        "subCategoryId", 659,
                        "source", "AD_SOURCE_DESKTOP",
                        "pagination", Map.of(
                                "limit", 78,
                                "skip", 0
                        ),
                        "filters", List.of(
                                Map.of(
                                        "filterId", 16,
                                        "features", List.of(
                                                Map.of(
                                                        "featureId", 1,
                                                        "optionIds", List.of(776)
                                                )
                                        )
                                ),
                                Map.of(
                                        "filterId", 1,
                                        "features", List.of(
                                                Map.of(
                                                        "featureId", 2095,
                                                        "optionIds", List.of(s.generationId)
                                                )
                                        )
                                ),
                                Map.of(
                                        "filterId", 7,
                                        "features", List.of(
                                                Map.of(
                                                        "featureId", 19,
                                                        "range", Map.of(
                                                                "min", s.minYear,
                                                                "max", s.maxYear
                                                        )
                                                )
                                        )
                                ),
                                Map.of(
                                        "filterId", 1081,
                                        "features", List.of(
                                                Map.of(
                                                        "featureId", 104,
                                                        "range", Map.of(
                                                                "min", s.minMileage,
                                                                "max", s.maxMileage
                                                        ),
                                                        "unit", "UNIT_KILOMETER"
                                                )
                                        )
                                )
                        )
                )
        );
    }

    public GraphQLRequest returnRequestBody(UserSession userSession) {
        return new GraphQLRequest(QUERY, modifyVariables(userSession));
    }

}
