package com.yakupovdev.stringmatch.algorithm;

import com.yakupovdev.stringmatch.model.SearchRequest;
import com.yakupovdev.stringmatch.model.SearchResult;

import java.util.ArrayList;
import java.util.List;


public class RabinKarpAlgorithm {

    private static final long PRIME = 101;
    private static final long BASE = 256;

    private long hashCollisions = 0;
    private long comparisons = 0;


    public List<SearchResult> processRequests(List<SearchRequest> requests) {
        List<SearchResult> results = new ArrayList<>();

        for (SearchRequest request : requests) {
            System.out.println("Processing: " + request.getId() + " - " + request.getDescription());
            SearchResult result = search(request);
            results.add(result);

            if (result.isFound()) {
                System.out.printf("  Found %d occurrence(s) in %.3f ms%n",
                        result.getOccurrences().size(), result.getExecutionTimeMs());
            } else {
                System.out.printf("  Not found (%.3f ms)%n", result.getExecutionTimeMs());
            }
        }

        return results;
    }


    public SearchResult search(SearchRequest request) {
        hashCollisions = 0;
        comparisons = 0;

        SearchResult result = new SearchResult();
        result.setRequestId(request.getId());
        result.setText(truncateForDisplay(request.getText(), 50));
        result.setPattern(request.getPattern());
        result.setDescription(request.getDescription());
        result.setTextLength(request.getText().length());
        result.setPatternLength(request.getPattern().length());

        long startTime = System.nanoTime();

        List<Integer> occurrences = searchAllOccurrences(request.getText(), request.getPattern());

        long endTime = System.nanoTime();
        double executionTimeMs = (endTime - startTime) / 1_000_000.0;

        result.setOccurrences(occurrences);
        result.setFound(!occurrences.isEmpty());
        result.setExecutionTimeMs(executionTimeMs);
        result.setHashCollisions(hashCollisions);
        result.setComparisons(comparisons);

        return result;
    }


    private List<Integer> searchAllOccurrences(String text, String pattern) {
        List<Integer> occurrences = new ArrayList<>();

        if (text == null || pattern == null || pattern.isEmpty()) {
            return occurrences;
        }

        int n = text.length();
        int m = pattern.length();

        if (m > n) {
            return occurrences;
        }

        long patternHash = 0;
        long textHash = 0;
        long h = 1;


        for (int i = 0; i < m - 1; i++) {
            h = (h * BASE) % PRIME;
        }


        for (int i = 0; i < m; i++) {
            patternHash = (BASE * patternHash + pattern.charAt(i)) % PRIME;
            textHash = (BASE * textHash + text.charAt(i)) % PRIME;
        }

        for (int i = 0; i <= n - m; i++) {
            if (patternHash == textHash) {
                boolean match = true;
                for (int j = 0; j < m; j++) {
                    comparisons++;
                    if (text.charAt(i + j) != pattern.charAt(j)) {
                        match = false;
                        hashCollisions++;
                        break;
                    }
                }

                if (match) {
                    occurrences.add(i);
                }
            }

            if (i < n - m) {
                textHash = (BASE * (textHash - text.charAt(i) * h) + text.charAt(i + m)) % PRIME;

                if (textHash < 0) {
                    textHash = (textHash + PRIME);
                }
            }
        }

        return occurrences;
    }

    private String truncateForDisplay(String text, int maxLength) {
        if (text == null) {
            return "";
        }
        if (text.length() <= maxLength) {
            return text;
        }
        return text.substring(0, maxLength) + "...";
    }
}