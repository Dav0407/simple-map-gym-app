package com.epam_task.storage_test;

import com.epam_task.domain.Training;
import com.epam_task.storage.TrainingStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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