package com.yakupovdev.stringmatch;

import com.yakupovdev.stringmatch.algorithm.RabinKarpAlgorithm;
import com.yakupovdev.stringmatch.model.SearchRequest;
import com.yakupovdev.stringmatch.model.SearchResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class RabinKarpAlgorithmTest {

    private RabinKarpAlgorithm algorithm;

    @BeforeEach
    void setUp() {
        algorithm = new RabinKarpAlgorithm();
    }

    @Test
    void testSimplePatternFound() {
        SearchRequest request = new SearchRequest(
                "TEST-1",
                "ABCDEFGH",
                "CDE",
                "Simple test"
        );

        SearchResult result = algorithm.search(request);

        assertTrue(result.isFound());
        assertEquals(1, result.getOccurrences().size());
        assertEquals(2, result.getOccurrences().get(0));
    }

    @Test
    void testPatternNotFound() {
        SearchRequest request = new SearchRequest(
                "TEST-2",
                "ABCDEFGH",
                "XYZ",
                "Pattern not found"
        );

        SearchResult result = algorithm.search(request);

        assertFalse(result.isFound());
        assertEquals(0, result.getOccurrences().size());
    }

    @Test
    void testMultipleOccurrences() {
        SearchRequest request = new SearchRequest(
                "TEST-3",
                "AABAACAADAABAABA",
                "AABA",
                "Multiple occurrences"
        );

        SearchResult result = algorithm.search(request);

        assertTrue(result.isFound());
        assertEquals(3, result.getOccurrences().size());
        assertTrue(result.getOccurrences().contains(0));
        assertTrue(result.getOccurrences().contains(9));
        assertTrue(result.getOccurrences().contains(12));
    }

    @Test
    void testEmptyPattern() {
        SearchRequest request = new SearchRequest(
                "TEST-4",
                "ABCDEFGH",
                "",
                "Empty pattern"
        );

        SearchResult result = algorithm.search(request);

        assertFalse(result.isFound());
        assertEquals(0, result.getOccurrences().size());
    }

    @Test
    void testPatternLongerThanText() {
        SearchRequest request = new SearchRequest(
                "TEST-5",
                "ABC",
                "ABCDEF",
                "Pattern longer than text"
        );

        SearchResult result = algorithm.search(request);

        assertFalse(result.isFound());
        assertEquals(0, result.getOccurrences().size());
    }

    @Test
    void testSingleCharacterPattern() {
        SearchRequest request = new SearchRequest(
                "TEST-6",
                "ABCABC",
                "A",
                "Single character"
        );

        SearchResult result = algorithm.search(request);

        assertTrue(result.isFound());
        assertEquals(2, result.getOccurrences().size());
    }

    @Test
    void testPerformanceMetrics() {
        SearchRequest request = new SearchRequest(
                "TEST-7",
                "The quick brown fox",
                "fox",
                "Metrics test"
        );

        SearchResult result = algorithm.search(request);

        assertTrue(result.isFound());
        assertTrue(result.getExecutionTimeMs() >= 0);
        assertTrue(result.getComparisons() > 0);
    }
}