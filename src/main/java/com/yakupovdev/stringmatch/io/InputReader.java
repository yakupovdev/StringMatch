package com.yakupovdev.stringmatch.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.yakupovdev.stringmatch.model.SearchRequest;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Reads search requests from JSON file
 */
public class InputReader {

    private final Gson gson;

    public InputReader() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    /**
     * Read search requests from JSON file
     *
     * @param filename Path to JSON file
     * @return List of search requests
     * @throws IOException If file cannot be read
     */
    public List<SearchRequest> readFromJson(String filename) throws IOException {
        try (Reader reader = new FileReader(filename)) {
            Type listType = new TypeToken<List<SearchRequest>>() {
            }.getType();
            List<SearchRequest> requests = gson.fromJson(reader, listType);

            if (requests == null || requests.isEmpty()) {
                throw new IOException("No search requests found in " + filename);
            }

            return requests;
        }
    }
}