package com.epam_task.storage_test;

import com.epam_task.domain.Trainer;
import com.epam_task.storage.TrainerStorage;
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


class TrainerStorageTest {

    private static final String TEST_FILE_PATH = "test/path/trainers.json";

    private TrainerStorage trainerStorage;
    private Trainer testTrainer;
    private UUID testId;

    @BeforeEach
    void setUp() {
        trainerStorage = new TrainerStorage(TEST_FILE_PATH);

        testId = UUID.randomUUID();
        testTrainer = new Trainer();
        testTrainer.setUserId(testId);
        testTrainer.setUsername("TestTrainer");
    }

    @Test
    void postProcessor_WithValidTrainers_ShouldLoadTrainers() {

        List<Trainer> trainers = new ArrayList<>();
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        Trainer trainer1 = new Trainer();
        trainer1.setUserId(id1);
        trainer1.setUsername("Trainer1");

        Trainer trainer2 = new Trainer();
        trainer2.setUserId(id2);
        trainer2.setUsername("Trainer2");

        trainers.add(trainer1);
        trainers.add(trainer2);

        try (MockedConstruction<JsonFileReader> mocked = mockConstruction(JsonFileReader.class,
                (mock, context) -> {
                    when(mock.readJsonFile(eq(TEST_FILE_PATH), any(TypeReference.class)))
                            .thenReturn(trainers);
                })) {

            trainerStorage.postProcessor();

            assertEquals(2, trainerStorage.getTrainers().size());
            assertEquals(trainer1, trainerStorage.getTrainers().get(id1));
            assertEquals(trainer2, trainerStorage.getTrainers().get(id2));
        }
    }

    @Test
    void postProcessor_WithEmptyList_ShouldNotSaveAnyTrainers() {

        List<Trainer> emptyList = new ArrayList<>();

        try (MockedConstruction<JsonFileReader> mocked = mockConstruction(JsonFileReader.class,
                (mock, context) -> {
                    when(mock.readJsonFile(eq(TEST_FILE_PATH), any(TypeReference.class)))
                            .thenReturn(emptyList);
                })) {

            trainerStorage.postProcessor();

            assertTrue(trainerStorage.getTrainers().isEmpty());
        }
    }

    @Test
    void postProcessor_WithNullList_ShouldNotSaveAnyTrainers() {

        try (MockedConstruction<JsonFileReader> mocked = mockConstruction(JsonFileReader.class,
                (mock, context) -> {
                    when(mock.readJsonFile(eq(TEST_FILE_PATH), any(TypeReference.class)))
                            .thenReturn(null);
                })) {

            trainerStorage.postProcessor();

            assertTrue(trainerStorage.getTrainers().isEmpty());
        }
    }

    @Test
    void postProcessor_WithException_ShouldHandleException() {

        try (MockedConstruction<JsonFileReader> mocked = mockConstruction(JsonFileReader.class,
                (mock, context) -> {
                    when(mock.readJsonFile(eq(TEST_FILE_PATH), any(TypeReference.class)))
                            .thenThrow(new RuntimeException("Test exception"));
                })) {

            trainerStorage.postProcessor();

            assertTrue(trainerStorage.getTrainers().isEmpty());
        }
    }

    @Test
    void save_WithValidTrainer_ShouldSaveAndReturn() {

        Trainer previousValue = trainerStorage.save(testTrainer);

        assertNotNull(previousValue);
        assertEquals(testTrainer, trainerStorage.getTrainers().get(testId));
    }

    @Test
    void save_WithNullTrainer_ShouldReturnNull() {

        Trainer result = trainerStorage.save(null);

        assertNull(result);
        assertTrue(trainerStorage.getTrainers().isEmpty());
    }

    @Test
    void save_WithNullId_ShouldReturnNull() {

        testTrainer.setUserId(null);

        Trainer result = trainerStorage.save(testTrainer);

        assertNull(result);
        assertTrue(trainerStorage.getTrainers().isEmpty());
    }

    @Test
    void findById_WithExistingId_ShouldReturnTrainer() {

        trainerStorage.save(testTrainer);

        Trainer result = trainerStorage.findById(testId);

        assertEquals(testTrainer, result);
    }

    @Test
    void findById_WithNonExistingId_ShouldReturnNull() {

        Trainer result = trainerStorage.findById(UUID.randomUUID());

        assertNull(result);
    }

    @Test
    void findById_WithNullId_ShouldReturnNull() {

        Trainer result = trainerStorage.findById(null);

        assertNull(result);
    }

    @Test
    void updateById_WithExistingId_ShouldUpdateAndReturn() {

        trainerStorage.save(testTrainer);

        Trainer updatedTrainer = new Trainer();
        updatedTrainer.setUserId(testId);
        updatedTrainer.setUsername("UpdatedTrainer");

        Trainer previousValue = trainerStorage.updateById(testId, updatedTrainer);

        assertEquals(updatedTrainer, previousValue);
        assertEquals(updatedTrainer, trainerStorage.getTrainers().get(testId));
    }

    @Test
    void updateById_WithNonExistingId_ShouldReturnNull() {

        Trainer result = trainerStorage.updateById(UUID.randomUUID(), testTrainer);

        assertNull(result);
    }

    @Test
    void updateById_WithNullId_ShouldReturnNull() {

        Trainer result = trainerStorage.updateById(null, testTrainer);

        assertNull(result);
    }

    @Test
    void updateById_WithNullTrainer_ShouldReturnNull() {

        trainerStorage.save(testTrainer);

        Trainer result = trainerStorage.updateById(testId, null);

        assertNull(result);
    }

    @Test
    void getTrainers_ShouldReturnMapOfTrainers() {

        trainerStorage.save(testTrainer);

        Map<UUID, Trainer> trainers = trainerStorage.getTrainers();

        assertNotNull(trainers);
        assertEquals(1, trainers.size());
        assertEquals(testTrainer, trainers.get(testId));
    }
}