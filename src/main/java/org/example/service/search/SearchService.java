package org.example.service.search;

import org.example.common.HttpService;
import org.example.model.dto.request.*;
import org.example.model.entity.UserSession;

import java.io.IOException;
import java.util.List;

public class SearchService {

    private static final String GRAPHQL_ENDPOINT = "https://999.md/graphql";

    private final HttpService httpService;
    private final SearchRequestBuilder requestBuilder;

    public SearchService() {
        this.httpService = new HttpService();
        this.requestBuilder = new SearchRequestBuilder();
    }

    public String search(UserSession session)
            throws IOException, InterruptedException {

        GraphQLRequest requestBody = requestBuilder.buildRequest(session);
        return httpService.postJson(GRAPHQL_ENDPOINT, requestBody);
    }
}
