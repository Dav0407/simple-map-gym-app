package com.epam_task.storage_test;

import com.epam_task.domain.Training;
import com.epam_task.storage.TrainingStorage;
import com.epam_task.utils.JsonFileReader;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.when;

class TrainingStorageTest {

    private static final String TEST_FILE_PATH = "test/path/trainings.json";

    private TrainingStorage trainingStorage;
    private Training testTraining;
    private UUID testId;

    @BeforeEach
    void setUp() {
        trainingStorage = new TrainingStorage(TEST_FILE_PATH);

        testId = UUID.randomUUID();
        testTraining = new Training();
        testTraining.setId(testId);
        testTraining.setTrainingName("TestTraining");
    }

    @Test
    void postProcessor_WithValidTrainings_ShouldLoadTrainings() {

        List<Training> trainings = new ArrayList<>();
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        Training training1 = new Training();
        training1.setId(id1);
        training1.setTrainingName("Java Basics");

        Training training2 = new Training();
        training2.setId(id2);
        training2.setTrainingName("Spring Framework");

        trainings.add(training1);
        trainings.add(training2);

        try (MockedConstruction<JsonFileReader> mocked = mockConstruction(JsonFileReader.class,
                (mock, context) -> {
                    when(mock.readJsonFile(eq(TEST_FILE_PATH), any(TypeReference.class)))
                            .thenReturn(trainings);
                })) {

            trainingStorage.postProcessor();

            assertEquals(2, trainingStorage.getTrainings().size());
            assertEquals(training1, trainingStorage.getTrainings().get(id1));
            assertEquals(training2, trainingStorage.getTrainings().get(id2));
        }
    }

    @Test
    void postProcessor_WithEmptyList_ShouldNotSaveAnyTrainings() {

        List<Training> emptyList = new ArrayList<>();

        try (MockedConstruction<JsonFileReader> mocked = mockConstruction(JsonFileReader.class,
                (mock, context) -> {
                    when(mock.readJsonFile(eq(TEST_FILE_PATH), any(TypeReference.class)))
                            .thenReturn(emptyList);
                })) {

            trainingStorage.postProcessor();

            assertTrue(trainingStorage.getTrainings().isEmpty());
        }
    }

    @Test
    void postProcessor_WithNullList_ShouldNotSaveAnyTrainings() {

        try (MockedConstruction<JsonFileReader> mocked = mockConstruction(JsonFileReader.class,
                (mock, context) -> {
                    when(mock.readJsonFile(eq(TEST_FILE_PATH), any(TypeReference.class)))
                            .thenReturn(null);
                })) {

            trainingStorage.postProcessor();

            assertTrue(trainingStorage.getTrainings().isEmpty());
        }
    }

    @Test
    void postProcessor_WithException_ShouldHandleException() {

        try (MockedConstruction<JsonFileReader> mocked = mockConstruction(JsonFileReader.class,
                (mock, context) -> {
                    when(mock.readJsonFile(eq(TEST_FILE_PATH), any(TypeReference.class)))
                            .thenThrow(new RuntimeException("Test exception"));
                })) {

            trainingStorage.postProcessor();

            assertTrue(trainingStorage.getTrainings().isEmpty());
        }
    }

    @Test
    void save_WithValidTraining_ShouldSaveAndReturn() {

        Training previousValue = trainingStorage.save(testTraining);

        assertNotNull(previousValue);
        assertEquals(testTraining, trainingStorage.getTrainings().get(testId));
    }

    @Test
    void save_WithNullTraining_ShouldReturnNull() {

        Training result = trainingStorage.save(null);

        assertNull(result);
        assertTrue(trainingStorage.getTrainings().isEmpty());
    }

    @Test
    void save_WithNullId_ShouldReturnNull() {

        testTraining.setId(null);

        Training result = trainingStorage.save(testTraining);

        assertNull(result);
        assertTrue(trainingStorage.getTrainings().isEmpty());
    }

    @Test
    void findById_WithExistingId_ShouldReturnTraining() {

        trainingStorage.save(testTraining);

        Training result = trainingStorage.findById(testId);

        assertEquals(testTraining, result);
    }

    @Test
    void findById_WithNonExistingId_ShouldReturnNull() {

        Training result = trainingStorage.findById(UUID.randomUUID());

        assertNull(result);
    }

    @Test
    void findById_WithNullId_ShouldReturnNull() {

        Training result = trainingStorage.findById(null);

        assertNull(result);
    }

    @Test
    void getTrainings_ShouldReturnMapOfTrainings() {

        trainingStorage.save(testTraining);

        Map<UUID, Training> trainings = trainingStorage.getTrainings();

        assertNotNull(trainings);
        assertEquals(1, trainings.size());
        assertEquals(testTraining, trainings.get(testId));
    }
}