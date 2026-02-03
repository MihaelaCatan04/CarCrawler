package org.example.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.bot.CarOptions;
import org.example.bot.GenerationOption;
import org.example.bot.ModelOption;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CarOptionsRequestService {

    private static final String GRAPHQL_ENDPOINT = "https://999.md/graphql";

    public CarOptions fetch(int brandId)
            throws IOException, InterruptedException {

        CarOptionsRequestEditorService editor = new CarOptionsRequestEditorService();
        GraphQLRequest requestBody = editor.returnRequestBody(brandId);

        ObjectMapper mapper = new ObjectMapper();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(GRAPHQL_ENDPOINT))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(
                        mapper.writeValueAsString(requestBody)))
                .build();

        HttpClient client = HttpClient.newHttpClient();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonObject jsonResponse =
                JsonParser.parseString(response.body()).getAsJsonObject();

        return parseCarOptions(jsonResponse);
    }

    private CarOptions parseCarOptions(JsonObject response) {

        CarOptions result = new CarOptions();

        JsonArray models = response
                .getAsJsonObject("data")
                .getAsJsonObject("feature")
                .getAsJsonArray("options");

        for (int i = 0; i < models.size(); i++) {

            JsonObject modelObj = models.get(i).getAsJsonObject();

            int modelId = modelObj.get("id").getAsInt();
            String modelTitle =
                    modelObj.getAsJsonObject("title")
                            .get("translated").getAsString();

            ModelOption model = new ModelOption(modelId, modelTitle);

            if (modelObj.has("feature") && !modelObj.get("feature").isJsonNull()) {

                JsonArray generations =
                        modelObj.getAsJsonObject("feature")
                                .getAsJsonArray("options");

                for (int j = 0; j < generations.size(); j++) {

                    JsonObject genObj = generations.get(j).getAsJsonObject();

                    int genId = genObj.get("id").getAsInt();
                    String genTitle =
                            genObj.getAsJsonObject("title")
                                    .get("translated").getAsString();

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
