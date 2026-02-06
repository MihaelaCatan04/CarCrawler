package org.example.service.options;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.common.HttpService;
import org.example.model.dto.request.FeatureRequestInput;
import org.example.model.dto.request.FeatureRequestVariables;
import org.example.model.dto.request.GraphQLRequest;
import org.example.model.dto.response.Feature;
import org.example.model.dto.response.FeatureData;
import org.example.model.dto.response.GraphQLResponse;
import org.example.model.dto.response.Option;
import org.example.model.entity.CarOptions;
import org.example.model.entity.GenerationOption;
import org.example.model.entity.ModelOption;
import org.example.telegram.message.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class CarOptionsService {

    private static final String GRAPHQL_ENDPOINT = "https://999.md/graphql";

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

    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpService httpService;
    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

    public CarOptionsService() {
        this.httpService = new HttpService();
    }

    public CarOptionsService(HttpService httpService) {
        this.httpService = httpService;
    }

    public CarOptions fetch(int brandId)
            throws IOException, InterruptedException {

        GraphQLRequest requestBody = buildRequest(brandId);
        String responseBody = httpService.postJson(GRAPHQL_ENDPOINT, requestBody);

        GraphQLResponse graphQLResponse =
                mapper.readValue(responseBody, GraphQLResponse.class);

        return parseCarOptions(graphQLResponse);
    }

    private GraphQLRequest buildRequest(int brandId) {
        FeatureRequestInput input = new FeatureRequestInput(659, 21, brandId);
        FeatureRequestVariables variables = new FeatureRequestVariables(input);
        return new GraphQLRequest(QUERY, variables);
    }

    private CarOptions parseCarOptions(GraphQLResponse response) {
        CarOptions result = new CarOptions();

        FeatureData data = response.data();
        if (data == null || data.feature() == null) {
            return result;
        }

        List<Option> models = data.feature().options();
        logger.info("Found {} models", models.size());
        logger.info(models.toString());
        if (models == null) {
            return result;
        }

        for (Option modelOption : models) {
            if (modelOption.feature().options().isEmpty()) {
                continue;
            }
            int modelId = modelOption.id();
            String modelTitle = modelOption.title().translated();

            ModelOption model = new ModelOption(modelId, modelTitle);

            Feature generationFeature = modelOption.feature();
            if (generationFeature != null && generationFeature.options() != null) {
                for (Option genOption : generationFeature.options()) {
                    model.addGeneration(
                            new GenerationOption(
                                    genOption.id(),
                                    genOption.title().translated()
                            )
                    );
                }
            }

            result.addModel(model);
        }

        return result;
    }
}
