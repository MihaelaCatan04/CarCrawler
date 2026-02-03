package org.example.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.bot.CarOptions;
import org.example.bot.GenerationOption;
import org.example.bot.ModelOption;
import org.example.car_options_model.Data;
import org.example.car_options_model.Feature;
import org.example.car_options_model.Option;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class CarOptionsRequestService {

    private static final String GRAPHQL_ENDPOINT = "https://999.md/graphql";
    private final ObjectMapper mapper = new ObjectMapper();

    public CarOptions fetch(int brandId)
            throws IOException, InterruptedException {

        CarOptionsRequestEditorService editor = new CarOptionsRequestEditorService();
        GraphQLRequest requestBody = editor.returnRequestBody(brandId);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(GRAPHQL_ENDPOINT))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(
                        mapper.writeValueAsString(requestBody)))
                .build();

        HttpClient client = HttpClient.newHttpClient();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        // Deserialize the response into DTOs
        GraphQLResponse graphQLResponse =
                mapper.readValue(response.body(), GraphQLResponse.class);

        return parseCarOptions(graphQLResponse);
    }

    private CarOptions parseCarOptions(GraphQLResponse response) {
        CarOptions result = new CarOptions();

        // Access data.feature.options
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

            // Check if the model has generations (nested feature.options)
            Feature generationFeature = modelOption.feature();
            if (generationFeature != null && generationFeature.options() != null) {
                List<Option> generations = generationFeature.options();

                for (Option genOption : generations) {
                    int genId = genOption.id();
                    String genTitle = genOption.title().translated();

                    model.generations.add(
                            new GenerationOption(genId, genTitle)
                    );
                }
            }

            result.models.add(model);
        }

        return result;
    }
}