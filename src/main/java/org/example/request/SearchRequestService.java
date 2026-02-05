package org.example.request;

import org.example.bot.UserSession;
import org.example.http.HttpService;

import java.io.IOException;

public class SearchRequestService {

    private static final String GRAPHQL_ENDPOINT = "https://999.md/graphql";

    private final HttpService httpService = new HttpService();

    public String search(UserSession session)
            throws IOException, InterruptedException {

        SearchRequestEditorService editor = new SearchRequestEditorService();
        GraphQLRequest requestBody = editor.returnRequestBody(session);

        return httpService.postJson(GRAPHQL_ENDPOINT, requestBody);
    }
}
