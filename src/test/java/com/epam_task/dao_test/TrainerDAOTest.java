package com.epam_task.dao_test;

import com.epam_task.dao.TrainerDAO;
import com.epam_task.domain.Trainer;
import com.epam_task.storage.TrainerStorage;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class TrainerDAOTest {

    @Mock
    private TrainerStorage storage;

    @InjectMocks
    private TrainerDAO trainerDAO;

    private Trainer testTrainer;
    private UUID testId;

    @BeforeEach
    void setUp() {
        testId = UUID.randomUUID();
        testTrainer = new Trainer();
    }

    @Test
    void constructorWithStorage_ShouldSetStorageField() {

        TrainerStorage newStorage = mock(TrainerStorage.class);

        TrainerDAO dao = new TrainerDAO(newStorage);

        assertEquals(newStorage, dao.getStorage());
    }

    @Test
    void save_ShouldCallStorageAndReturnSavedTrainer() {

        when(storage.save(testTrainer)).thenReturn(testTrainer);

        Trainer result = trainerDAO.save(testTrainer);

        assertNotNull(result);
        assertEquals(testTrainer, result);
        verify(storage).save(testTrainer);
    }

    @Test
    void read_ShouldReturnTrainerFromStorage() {

        when(storage.findById(testId)).thenReturn(testTrainer);

        Trainer result = trainerDAO.read(testId);

        assertNotNull(result);
        assertEquals(testTrainer, result);
        verify(storage).findById(testId);
    }

    @Test
    void update_ShouldCallStorageAndReturnUpdatedTrainer() {

        when(storage.updateById(testId, testTrainer)).thenReturn(testTrainer);

        Trainer result = trainerDAO.update(testId, testTrainer);

        assertNotNull(result);
        assertEquals(testTrainer, result);
        verify(storage).updateById(testId, testTrainer);
    }

    @Test
    void findAll_ShouldReturnListOfTrainers() {

        HashMap<UUID, Trainer> trainers = new HashMap<>();
        trainers.put(testId, testTrainer);
        when(storage.getTrainers()).thenReturn(trainers);

        List<Trainer> result = trainerDAO.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testTrainer, result.get(0));
        verify(storage).getTrainers();
    }

    @Test
    void setStorage_ShouldUpdateStorageField() {

        TrainerStorage newStorage = mock(TrainerStorage.class);

        trainerDAO.setStorage(newStorage);

        assertEquals(newStorage, trainerDAO.getStorage());
    }
}