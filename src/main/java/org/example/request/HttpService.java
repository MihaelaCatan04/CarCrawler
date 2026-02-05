package org.example.http;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.util.Map;

public class HttpService {

    private final HttpClient client;
    private final ObjectMapper mapper;

    public HttpService() {
        this.client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();

        this.mapper = new ObjectMapper();
    }

    public String postJson(String url, Object body)
            throws IOException, InterruptedException {

        String json = mapper.writeValueAsString(body);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }
    public String get(String url, Map<String, String> headers)
            throws IOException, InterruptedException {

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET();

        if (headers != null) {
            headers.forEach(builder::header);
        }

        HttpRequest request = builder.build();

        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }
}
