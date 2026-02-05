package org.example.request;

public class FeatureRequestInput {
    private final int categoryId;
    private final int id;
    private final int parentId;

    public FeatureRequestInput(int categoryId, int id, int parentId) {
        this.categoryId = categoryId;
        this.id = id;
        this.parentId = parentId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getId() {
        return id;
    }

    public int getParentId() {
        return parentId;
    }
}