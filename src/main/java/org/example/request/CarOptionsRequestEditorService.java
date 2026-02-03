package org.example.request;

import java.util.Map;

public class CarOptionsRequestEditorService {

    private static final String QUERY = """
            query FeatureOptions($input: FeatureRequestInput!) {
              feature(input: $input) {
                id
                options {
                  id
                  title { translated }
                  parentId
                  hasChildren
                  feature {
                    id
                    options {
                      id
                      title { translated }
                      parentId
                    }
                  }
                }
              }
            }
            """;

    private Map<String, Object> modifyVariables(int brandId) {

        return Map.of(
                "input", Map.of(
                        "categoryId", 659,
                        "id", 21,
                        "parentId", brandId
                )
        );
    }

    public GraphQLRequest returnRequestBody(int brandId) {
        return new GraphQLRequest(QUERY, modifyVariables(brandId));
    }
}
