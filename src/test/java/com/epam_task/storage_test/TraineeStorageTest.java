package com.epam_task.storage_test;

import com.epam_task.domain.Trainee;
import com.epam_task.storage.TraineeStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


class TraineeStorageTest {

    private static final String TEST_FILE_PATH = "test/path/trainees.json";

    private TraineeStorage traineeStorage;
    private Trainee testTrainee;
    private UUID testId;

    @BeforeEach
    void setUp() {
        traineeStorage = new TraineeStorage(TEST_FILE_PATH);

        testId = UUID.randomUUID();
        testTrainee = new Trainee();
        testTrainee.setUserId(testId);
        testTrainee.setUsername("TestUser");
    }

    @Test
    void save_WithValidTrainee_ShouldSaveAndReturn() {

        Trainee previousValue = traineeStorage.save(testTrainee);

        assertNotNull(previousValue);
        assertEquals(testTrainee, traineeStorage.getTrainees().get(testId));
    }

    @Test
    void save_WithNullTrainee_ShouldReturnNull() {

        Trainee result = traineeStorage.save(null);

        assertNull(result);
        assertTrue(traineeStorage.getTrainees().isEmpty());
    }

    @Test
    void save_WithNullId_ShouldReturnNull() {

        testTrainee.setUserId(null);

        Trainee result = traineeStorage.save(testTrainee);

        assertNull(result);
        assertTrue(traineeStorage.getTrainees().isEmpty());
    }

    @Test
    void findById_WithExistingId_ShouldReturnTrainee() {

        traineeStorage.save(testTrainee);

        Trainee result = traineeStorage.findById(testId);

        assertEquals(testTrainee, result);
    }

    @Test
    void findById_WithNonExistingId_ShouldReturnNull() {

        Trainee result = traineeStorage.findById(UUID.randomUUID());

        assertNull(result);
    }

    @Test
    void findById_WithNullId_ShouldReturnNull() {

        Trainee result = traineeStorage.findById(null);

        assertNull(result);
    }

    @Test
    void updateById_WithExistingId_ShouldUpdateAndReturn() {

        traineeStorage.save(testTrainee);

        Trainee updatedTrainee = new Trainee();
        updatedTrainee.setUserId(testId);
        updatedTrainee.setUsername("UpdatedUser");

        Trainee previousValue = traineeStorage.updateById(testId, updatedTrainee);

        assertEquals(updatedTrainee, previousValue);
        assertEquals(updatedTrainee, traineeStorage.getTrainees().get(testId));
    }

    @Test
    void updateById_WithNonExistingId_ShouldReturnNull() {

        Trainee result = traineeStorage.updateById(UUID.randomUUID(), testTrainee);

        assertNull(result);
    }

    @Test
    void updateById_WithNullId_ShouldReturnNull() {

        Trainee result = traineeStorage.updateById(null, testTrainee);

        assertNull(result);
    }

    @Test
    void updateById_WithNullTrainee_ShouldReturnNull() {

        traineeStorage.save(testTrainee);

        Trainee result = traineeStorage.updateById(testId, null);

        assertNull(result);
    }

    @Test
    void deleteById_WithExistingId_ShouldReturnTrue() {

        traineeStorage.save(testTrainee);

        boolean result = traineeStorage.deleteById(testId);

        assertTrue(result);
        assertFalse(traineeStorage.getTrainees().containsKey(testId));
    }

    @Test
    void deleteById_WithNonExistingId_ShouldReturnFalse() {

        boolean result = traineeStorage.deleteById(UUID.randomUUID());

        assertFalse(result);
    }

    @Test
    void deleteById_WithNullId_ShouldReturnFalse() {

        boolean result = traineeStorage.deleteById(null);

        assertFalse(result);
    }

    @Test
    void getTrainees_ShouldReturnMapOfTrainees() {

        traineeStorage.save(testTrainee);

        assertNotNull(traineeStorage.getTrainees());
        assertEquals(1, traineeStorage.getTrainees().size());
        assertEquals(testTrainee, traineeStorage.getTrainees().get(testId));
    }
}