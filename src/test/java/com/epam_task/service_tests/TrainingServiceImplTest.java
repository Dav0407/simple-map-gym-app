package com.epam_task.service_tests;

import com.epam_task.dao.TrainingDAO;
import com.epam_task.domain.Training;
import com.epam_task.enums.TrainingType;
import com.epam_task.service.TrainingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class TrainingServiceImplTest {

    @Mock
    private TrainingDAO trainingDAO;

    @InjectMocks
    private TrainingServiceImpl trainingService;

    private Training testTraining;

    @BeforeEach
    void setUp() {

        testTraining = new Training();
        testTraining.setId(UUID.randomUUID());
        testTraining.setTraineeId(UUID.randomUUID());
        testTraining.setTrainerId(UUID.randomUUID());
        testTraining.setTrainingName("Morning Session");
        testTraining.setTrainingType(TrainingType.CARDIO);
        testTraining.setTrainingDate(LocalDate.now().plusDays(1)); // Future date
        testTraining.setTrainingDuration(60);
    }

    @Test
    void createTraining_shouldCreateTraining() {

        when(trainingDAO.save(any(Training.class))).thenReturn(testTraining);

        Training createdTraining = trainingService.createTraining(
                testTraining.getTraineeId(),
                testTraining.getTrainerId(),
                testTraining.getTrainingName(),
                testTraining.getTrainingType(),
                testTraining.getTrainingDate(),
                testTraining.getTrainingDuration()
        );

        assertNotNull(createdTraining);
        assertEquals(testTraining.getTraineeId(), createdTraining.getTraineeId());
        assertEquals(testTraining.getTrainerId(), createdTraining.getTrainerId());
        assertEquals(testTraining.getTrainingName(), createdTraining.getTrainingName());
        assertEquals(testTraining.getTrainingType(), createdTraining.getTrainingType());
        assertEquals(testTraining.getTrainingDate(), createdTraining.getTrainingDate());
        assertEquals(testTraining.getTrainingDuration(), createdTraining.getTrainingDuration());

        verify(trainingDAO).save(any(Training.class));
    }

    @Test
    void createTraining_shouldThrowWhenTraineeIdOrTrainerIdIsNull() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> trainingService.createTraining(null, UUID.randomUUID(), "Morning Session", TrainingType.CARDIO, LocalDate.now().plusDays(1), 60));

        assertEquals("Trainee ID and Trainer ID cannot be null.", exception.getMessage());


        verifyNoInteractions(trainingDAO);
    }

    @Test
    void createTraining_shouldThrowWhenTrainingNameIsEmpty() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> trainingService.createTraining(UUID.randomUUID(), UUID.randomUUID(), "", TrainingType.CARDIO, LocalDate.now().plusDays(1), 60));

        assertEquals("Training name cannot be empty.", exception.getMessage());

        verifyNoInteractions(trainingDAO);
    }

    @Test
    void createTraining_shouldThrowWhenTrainingTypeIsNull() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> trainingService.createTraining(UUID.randomUUID(), UUID.randomUUID(), "Morning Session", null, LocalDate.now().plusDays(1), 60));

        assertEquals("Training type cannot be null.", exception.getMessage());

        verifyNoInteractions(trainingDAO);
    }

    @Test
    void createTraining_shouldThrowWhenTrainingDateIsInPast() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> trainingService.createTraining(UUID.randomUUID(), UUID.randomUUID(), "Morning Session", TrainingType.CARDIO, LocalDate.now().minusDays(1), 60));

        assertEquals("Training date cannot be in the past.", exception.getMessage());

        verifyNoInteractions(trainingDAO);
    }

    @Test
    void createTraining_shouldThrowWhenTrainingDurationIsInvalid() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> trainingService.createTraining(UUID.randomUUID(), UUID.randomUUID(), "Morning Session", TrainingType.CARDIO, LocalDate.now().plusDays(1), 0));

        assertEquals("Training duration must be a positive number.", exception.getMessage());

        verifyNoInteractions(trainingDAO);
    }

    @Test
    void save_shouldSaveTraining() {

        when(trainingDAO.save(any(Training.class))).thenReturn(testTraining);

        Training savedTraining = trainingService.save(testTraining);

        assertNotNull(savedTraining);
        assertEquals(testTraining.getId(), savedTraining.getId());
        verify(trainingDAO).save(testTraining);
    }

    @Test
    void save_shouldThrowWhenTrainingIsNull() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> trainingService.save(null));

        assertEquals("Training object cannot be null.", exception.getMessage());

        verifyNoInteractions(trainingDAO);
    }

    @Test
    void read_shouldReturnTraining() {

        UUID trainingId = testTraining.getId();
        when(trainingDAO.read(trainingId)).thenReturn(testTraining);

        Training foundTraining = trainingService.read(trainingId);

        assertNotNull(foundTraining);
        assertEquals(trainingId, foundTraining.getId());
        verify(trainingDAO).read(trainingId);
    }

    @Test
    void read_shouldThrowWhenIdIsNull() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> trainingService.read(null));

        assertEquals("Training ID cannot be null.", exception.getMessage());

        verifyNoInteractions(trainingDAO);
    }
}