package org.example.model.dto.request;

public class GraphQLRequest {
    private final String query;
    private final Object variables;

    public GraphQLRequest(String query, Object variables) {
        this.query = query;
        this.variables = variables;
    }

    public String getQuery() {
        return query;
    }

    public Object getVariables() {
        return variables;
    }
}
