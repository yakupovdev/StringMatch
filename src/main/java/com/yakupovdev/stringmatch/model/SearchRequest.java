package com.yakupovdev.stringmatch.model;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a search request with text and pattern
 */
public class SearchRequest {

    @SerializedName("id")
    private String id;

    @SerializedName("text")
    private String text;

    @SerializedName("pattern")
    private String pattern;

    @SerializedName("description")
    private String description;

    public SearchRequest() {
    }

    public SearchRequest(String id, String text, String pattern, String description) {
        this.id = id;
        this.text = text;
        this.pattern = pattern;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "SearchRequest{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", pattern='" + pattern + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}