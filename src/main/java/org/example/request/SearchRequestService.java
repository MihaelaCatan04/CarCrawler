package org.example.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.bot.UserSession;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SearchRequestService {
    private static final String GRAPHQL_ENDPOINT = "https://999.md/graphql";

    public String search(UserSession session) throws IOException, InterruptedException {
        SearchRequestEditorService editor = new SearchRequestEditorService();
        GraphQLRequest requestBody = editor.returnRequestBody(session);
        ObjectMapper mapper = new ObjectMapper();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(GRAPHQL_ENDPOINT))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(requestBody)))
                .build();

        HttpClient client = HttpClient.newHttpClient();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }


}
