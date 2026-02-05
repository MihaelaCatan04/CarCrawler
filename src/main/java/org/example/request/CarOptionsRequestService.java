package org.example.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.bot.CarOptions;
import org.example.bot.GenerationOption;
import org.example.bot.ModelOption;
import org.example.car_options_model.Data;
import org.example.car_options_model.Feature;
import org.example.car_options_model.Option;
import org.example.http.HttpService;

import java.io.IOException;
import java.util.List;

public class CarOptionsRequestService {

    private static final String GRAPHQL_ENDPOINT = "https://999.md/graphql";

    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpService httpService = new HttpService();

    public CarOptions fetch(int brandId)
            throws IOException, InterruptedException {

        CarOptionsRequestEditorService editor = new CarOptionsRequestEditorService();
        GraphQLRequest requestBody = editor.returnRequestBody(brandId);

        String responseBody = httpService.postJson(GRAPHQL_ENDPOINT, requestBody);
        System.out.println(responseBody);

        GraphQLResponse graphQLResponse =
                mapper.readValue(responseBody, GraphQLResponse.class);

        return parseCarOptions(graphQLResponse);
    }

    private CarOptions parseCarOptions(GraphQLResponse response) {
        CarOptions result = new CarOptions();

        Data data = response.data();
        if (data == null || data.feature() == null) {
            return result;
        }

        List<Option> models = data.feature().options();
        if (models == null) {
            return result;
        }

        for (Option modelOption : models) {
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
