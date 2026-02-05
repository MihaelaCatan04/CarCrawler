package org.example.request;

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

    private FeatureRequestVariables modifyVariables(int brandId) {
        FeatureRequestInput input = new FeatureRequestInput(659, 21, brandId);
        return new FeatureRequestVariables(input);
    }

    public GraphQLRequest returnRequestBody(int brandId) {
        return new GraphQLRequest(QUERY, modifyVariables(brandId));
    }
}