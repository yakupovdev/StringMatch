package com.yakupovdev.stringmatch;

import com.yakupovdev.stringmatch.io.InputReader;
import com.yakupovdev.stringmatch.algorithm.RabinKarpAlgorithm;
import com.yakupovdev.stringmatch.io.OutputWriter;
import com.yakupovdev.stringmatch.model.SearchRequest;
import com.yakupovdev.stringmatch.model.SearchResult;

import java.util.List;


public class Main {
    public static void main(String[] args) {
        System.out.println("=== Rabin-Karp String Matching Algorithm ===");
        System.out.println("Reading input from: input.json");

        try {
            InputReader inputReader = new InputReader();
            List<SearchRequest> requests = inputReader.readFromJson("input.json");

            System.out.println("Loaded " + requests.size() + " search request(s)\n");

            RabinKarpAlgorithm algorithm = new RabinKarpAlgorithm();
            List<SearchResult> results = algorithm.processRequests(requests);

            OutputWriter outputWriter = new OutputWriter();
            outputWriter.writeToCSV(results, "output.csv");

            System.out.println("\nResults written to: output.csv");
            System.out.println("Metrics written to: metrics.csv");

            printSummary(results);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void printSummary(List<SearchResult> results) {
        System.out.println("\n=== Summary ===");
        long totalMatches = results.stream()
                .mapToLong(r -> r.getOccurrences().size())
                .sum();

        double avgTime = results.stream()
                .mapToDouble(SearchResult::getExecutionTimeMs)
                .average()
                .orElse(0.0);

        System.out.println("Total searches: " + results.size());
        System.out.println("Total matches found: " + totalMatches);
        System.out.printf("Average execution time: %.3f ms%n", avgTime);
    }
}