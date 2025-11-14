package com.yakupovdev.stringmatch.io;

import com.opencsv.CSVWriter;
import com.yakupovdev.stringmatch.model.SearchResult;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes search results and metrics to CSV files
 */
public class OutputWriter {

    /**
     * Write search results to CSV file
     *
     * @param results List of search results
     * @param filename Output CSV filename
     * @throws IOException If file cannot be written
     */
    public void writeToCSV(List<SearchResult> results, String filename) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filename))) {
            String[] header = {
                    "Request ID",
                    "Description",
                    "Pattern",
                    "Text Preview",
                    "Text Length",
                    "Pattern Length",
                    "Found",
                    "Occurrences Count",
                    "Occurrence Indices",
                    "Execution Time (ms)",
                    "Hash Collisions",
                    "Comparisons"
            };
            writer.writeNext(header);

            for (SearchResult result : results) {
                String[] row = {
                        result.getRequestId(),
                        result.getDescription(),
                        result.getPattern(),
                        result.getText(),
                        String.valueOf(result.getTextLength()),
                        String.valueOf(result.getPatternLength()),
                        String.valueOf(result.isFound()),
                        String.valueOf(result.getOccurrences().size()),
                        result.getOccurrences().toString(),
                        String.format("%.3f", result.getExecutionTimeMs()),
                        String.valueOf(result.getHashCollisions()),
                        String.valueOf(result.getComparisons())
                };
                writer.writeNext(row);
            }
        }

        writeMetricsCSV(results, "metrics.csv");
    }

    /**
     * Write detailed metrics to separate CSV file
     */
    private void writeMetricsCSV(List<SearchResult> results, String filename) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filename))) {
            String[] header = {
                    "Metric",
                    "Value",
                    "Unit"
            };
            writer.writeNext(header);

            int totalSearches = results.size();
            long totalMatches = results.stream()
                    .mapToLong(r -> r.getOccurrences().size())
                    .sum();

            double avgExecutionTime = results.stream()
                    .mapToDouble(SearchResult::getExecutionTimeMs)
                    .average()
                    .orElse(0.0);

            double minExecutionTime = results.stream()
                    .mapToDouble(SearchResult::getExecutionTimeMs)
                    .min()
                    .orElse(0.0);

            double maxExecutionTime = results.stream()
                    .mapToDouble(SearchResult::getExecutionTimeMs)
                    .max()
                    .orElse(0.0);

            long totalCollisions = results.stream()
                    .mapToLong(SearchResult::getHashCollisions)
                    .sum();

            long totalComparisons = results.stream()
                    .mapToLong(SearchResult::getComparisons)
                    .sum();

            double avgTextLength = results.stream()
                    .mapToLong(SearchResult::getTextLength)
                    .average()
                    .orElse(0.0);

            double avgPatternLength = results.stream()
                    .mapToLong(SearchResult::getPatternLength)
                    .average()
                    .orElse(0.0);

            long successfulSearches = results.stream()
                    .filter(SearchResult::isFound)
                    .count();

            double successRate = (double) successfulSearches / totalSearches * 100;

            List<String[]> metrics = new ArrayList<>();
            metrics.add(new String[]{"Total Searches", String.valueOf(totalSearches), "count"});
            metrics.add(new String[]{"Successful Searches", String.valueOf(successfulSearches), "count"});
            metrics.add(new String[]{"Success Rate", String.format("%.2f", successRate), "%"});
            metrics.add(new String[]{"Total Matches Found", String.valueOf(totalMatches), "count"});
            metrics.add(new String[]{"Average Execution Time", String.format("%.3f", avgExecutionTime), "ms"});
            metrics.add(new String[]{"Min Execution Time", String.format("%.3f", minExecutionTime), "ms"});
            metrics.add(new String[]{"Max Execution Time", String.format("%.3f", maxExecutionTime), "ms"});
            metrics.add(new String[]{"Total Hash Collisions", String.valueOf(totalCollisions), "count"});
            metrics.add(new String[]{"Total Comparisons", String.valueOf(totalComparisons), "count"});
            metrics.add(new String[]{"Average Text Length", String.format("%.0f", avgTextLength), "characters"});
            metrics.add(new String[]{"Average Pattern Length", String.format("%.0f", avgPatternLength), "characters"});

            double avgComplexity = totalComparisons / (double) totalSearches;
            metrics.add(new String[]{"Average Comparisons per Search", String.format("%.2f", avgComplexity), "count"});

            for (String[] metric : metrics) {
                writer.writeNext(metric);
            }
        }
    }
}