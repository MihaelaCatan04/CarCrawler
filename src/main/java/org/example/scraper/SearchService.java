package org.example.scraper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.request.GraphQLRequest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SearchService {
    private static final String GRAPHQL_ENDPOINT = "https://999.md/graphql";
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
    private static final String PARAMETERS = """
            {
              "isWorkCategory": false,
              "includeCarsFeatures": true,
              "includeBody": false,
              "includeOwner": true,
              "includeBoost": false,
              "input": {
                "subCategoryId": 659,
                "source": "AD_SOURCE_DESKTOP",
                "filters": [
                  {
                    "filterId": 16,
                    "features": [
                      {
                        "featureId": 1,
                        "optionIds": [
                          776
                        ]
                      }
                    ]
                  },
                  {
                    "filterId": 1,
                    "features": [
                      {
                        "featureId": 2095,
                        "optionIds": [
                          36188
                        ]
                      }
                    ]
                  },
                  {
                    "filterId": 7,
                    "features": [
                      {
                        "featureId": 19,
                        "range": {
                          "max": 2016,
                          "min": 2015
                        }
                      }
                    ]
                  },
                  {
                    "filterId": 1081,
                    "features": [
                      {
                        "featureId": 104,
                        "range": {
                          "max": 300000,
                          "min": 200000
                        },
                        "unit": "UNIT_KILOMETER"
                      }
                    ]
                  }
                ],
                "pagination": {
                  "limit": 78,
                  "skip": 0
                }
              },
              "locale": "ro_RO"
            }
            """;

    private String returnRequestBody(String query, String parameters) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode variables = mapper.readTree(parameters);
        GraphQLRequest body = new GraphQLRequest(query, variables);
        return mapper.writeValueAsString(body);
    }


    public String search() throws IOException, InterruptedException {
        String requestBody = returnRequestBody(QUERY, PARAMETERS);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(GRAPHQL_ENDPOINT))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpClient client = HttpClient.newHttpClient();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }


}
