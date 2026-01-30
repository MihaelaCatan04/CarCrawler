package org.example.request;

public record GraphQLRequest(
        String query,
        Object variables
) {
}