package com.yakupovdev.stringmatch.model;

import java.util.List;

/**
 * Represents the result of a pattern search operation
 */
public class SearchResult {

    private String requestId;
    private String text;
    private String pattern;
    private String description;
    private List<Integer> occurrences;
    private boolean found;
    private long textLength;
    private long patternLength;
    private double executionTimeMs;
    private long hashCollisions;
    private long comparisons;

    public SearchResult() {
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
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

    public List<Integer> getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(List<Integer> occurrences) {
        this.occurrences = occurrences;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public long getTextLength() {
        return textLength;
    }

    public void setTextLength(long textLength) {
        this.textLength = textLength;
    }

    public long getPatternLength() {
        return patternLength;
    }

    public void setPatternLength(long patternLength) {
        this.patternLength = patternLength;
    }

    public double getExecutionTimeMs() {
        return executionTimeMs;
    }

    public void setExecutionTimeMs(double executionTimeMs) {
        this.executionTimeMs = executionTimeMs;
    }

    public long getHashCollisions() {
        return hashCollisions;
    }

    public void setHashCollisions(long hashCollisions) {
        this.hashCollisions = hashCollisions;
    }

    public long getComparisons() {
        return comparisons;
    }

    public void setComparisons(long comparisons) {
        this.comparisons = comparisons;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "requestId='" + requestId + '\'' +
                ", pattern='" + pattern + '\'' +
                ", found=" + found +
                ", occurrenceCount=" + (occurrences != null ? occurrences.size() : 0) +
                ", executionTimeMs=" + executionTimeMs +
                '}';
    }
}