package com.epam_task.storage_test;

import com.epam_task.domain.Trainer;
import com.epam_task.storage.TrainerStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


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