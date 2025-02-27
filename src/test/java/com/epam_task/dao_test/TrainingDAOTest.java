package com.epam_task.dao_test;

import com.epam_task.dao.TrainingDAO;
import com.epam_task.domain.Training;
import com.epam_task.storage.TrainingStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class TrainingDAOTest {

    @Mock
    private TrainingStorage storage;

    @InjectMocks
    private TrainingDAO trainingDAO;

    private Training testTraining;
    private UUID testId;

    @BeforeEach
    void setUp() {
        testId = UUID.randomUUID();
        testTraining = new Training();
    }

    @Test
    void constructorWithStorage_ShouldSetStorageField() {

        TrainingStorage newStorage = mock(TrainingStorage.class);

        TrainingDAO dao = new TrainingDAO(newStorage);

        assertEquals(newStorage, dao.getStorage());
    }

    @Test
    void save_ShouldCallStorageAndReturnSavedTraining() {

        when(storage.save(testTraining)).thenReturn(testTraining);

        Training result = trainingDAO.save(testTraining);

        assertNotNull(result);
        assertEquals(testTraining, result);
        verify(storage).save(testTraining);
    }

    @Test
    void read_ShouldReturnTrainingFromStorage() {

        when(storage.findById(testId)).thenReturn(testTraining);

        Training result = trainingDAO.read(testId);

        assertNotNull(result);
        assertEquals(testTraining, result);
        verify(storage).findById(testId);
    }

    @Test
    void setStorage_ShouldUpdateStorageField() {

        TrainingStorage newStorage = mock(TrainingStorage.class);

        trainingDAO.setStorage(newStorage);

        assertEquals(newStorage, trainingDAO.getStorage());
    }
}