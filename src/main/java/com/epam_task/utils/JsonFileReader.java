package com.epam_task.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonFileReader {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public <T> List<T> readJsonFile(String filePath, TypeReference<List<T>> typeReference) {

        OBJECT_MAPPER.registerModule(new JavaTimeModule());

        try {
            return OBJECT_MAPPER.readValue(new File(filePath), typeReference);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse JSON file: " + filePath, e);
        }
    }
}
