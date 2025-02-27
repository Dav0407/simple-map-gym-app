package com.epam_task.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class JsonFileReaderTest {

    private JsonFileReader jsonFileReader;

    @BeforeEach
    void setUp() {
        jsonFileReader = new JsonFileReader();
    }

    @Test
    void testReadJsonFile(@TempDir Path tempDir) throws IOException {
        // Create a temporary JSON file
        File jsonFile = tempDir.resolve("test.json").toFile();
        String jsonContent = "[{\"name\":\"John\",\"age\":30},{\"name\":\"Jane\",\"age\":25}]";
        try (FileWriter writer = new FileWriter(jsonFile)) {
            writer.write(jsonContent);
        }

        TypeReference<List<Person>> typeReference = new TypeReference<>() {};

        List<Person> people = jsonFileReader.readJsonFile(jsonFile.getAbsolutePath(), typeReference);

        assertNotNull(people);
        assertEquals(2, people.size());

        Person john = people.get(0);
        assertEquals("John", john.getName());
        assertEquals(30, john.getAge());

        Person jane = people.get(1);
        assertEquals("Jane", jane.getName());
        assertEquals(25, jane.getAge());
    }

    @Setter
    @Getter
    static class Person {
        private String name;
        private int age;
    }
}