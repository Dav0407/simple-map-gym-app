package com.epam_task.dao_test;

import com.epam_task.dao.TraineeDAO;
import com.epam_task.domain.Trainee;
import com.epam_task.storage.TraineeStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class TraineeDAOTest {

    @Mock
    private TraineeStorage storage;

    @InjectMocks
    private TraineeDAO traineeDAO;

    private Trainee testTrainee;
    private UUID testId;

    @BeforeEach
    void setUp() {
        testId = UUID.randomUUID();
        testTrainee = new Trainee();
    }

    @Test
    void save_ShouldCallStorageAndReturnSavedTrainee() {

        when(storage.save(testTrainee)).thenReturn(testTrainee);

        Trainee result = traineeDAO.save(testTrainee);

        assertNotNull(result);
        assertEquals(testTrainee, result);
        verify(storage).save(testTrainee);
    }

    @Test
    void read_ShouldReturnTraineeFromStorage() {

        when(storage.findById(testId)).thenReturn(testTrainee);

        Trainee result = traineeDAO.read(testId);

        assertNotNull(result);
        assertEquals(testTrainee, result);
        verify(storage).findById(testId);
    }

    @Test
    void update_ShouldCallStorageAndReturnUpdatedTrainee() {

        when(storage.updateById(testId, testTrainee)).thenReturn(testTrainee);

        Trainee result = traineeDAO.update(testId, testTrainee);

        assertNotNull(result);
        assertEquals(testTrainee, result);
        verify(storage).updateById(testId, testTrainee);
    }

    @Test
    void delete_ShouldCallStorageAndReturnResult() {

        when(storage.deleteById(testId)).thenReturn(true);

        boolean result = traineeDAO.delete(testId);

        assertTrue(result);
        verify(storage).deleteById(testId);
    }

    @Test
    void findAll_ShouldReturnListOfTrainees() {

        HashMap<UUID, Trainee> trainees = new HashMap<>();
        trainees.put(testId, testTrainee);
        when(storage.getTrainees()).thenReturn(trainees);

        List<Trainee> result = traineeDAO.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testTrainee, result.get(0));
        verify(storage).getTrainees();
    }

    @Test
    void setStorage_ShouldUpdateStorageField() {

        TraineeStorage newStorage = mock(TraineeStorage.class);

        traineeDAO.setStorage(newStorage);

        assertEquals(newStorage, traineeDAO.getStorage());
    }
}