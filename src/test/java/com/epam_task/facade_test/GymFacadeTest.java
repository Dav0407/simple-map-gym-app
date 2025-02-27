package com.epam_task.facade_test;

import com.epam_task.domain.Trainee;
import com.epam_task.domain.Trainer;
import com.epam_task.domain.Training;
import com.epam_task.enums.TrainingType;
import com.epam_task.facade.GymFacade;
import com.epam_task.service.TraineeService;
import com.epam_task.service.TrainerService;
import com.epam_task.service.TrainingService;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GymFacadeTest {

    @Mock
    private TraineeService traineeService;

    @Mock
    private TrainerService trainerService;

    @Mock
    private TrainingService trainingService;

    @InjectMocks
    private GymFacade gymFacade;

    private Trainee testTrainee;
    private Trainer testTrainer;
    private Training testTraining;
    private UUID testTraineeId;
    private UUID testTrainerId;
    private UUID testTrainingId;

    @BeforeEach
    void setUp() {
        testTraineeId = UUID.randomUUID();
        testTrainerId = UUID.randomUUID();
        testTrainingId = UUID.randomUUID();

        testTrainee = new Trainee();
        testTrainee.setUserId(testTraineeId);
        testTrainee.setFirstName("John");
        testTrainee.setLastName("Doe");

        testTrainer = new Trainer();
        testTrainer.setUserId(testTrainerId);
        testTrainer.setFirstName("Jane");
        testTrainer.setLastName("Smith");

        testTraining = new Training();
        testTraining.setId(testTrainingId);
        testTraining.setTrainingName("Morning Session");
        testTraining.setTrainingType(TrainingType.CARDIO);
    }

    @Test
    void createTrainee_shouldCallTraineeService() {

        when(traineeService.createTrainee("John", "Doe", "123 Main St", LocalDate.of(1990, 1, 1)))
                .thenReturn(testTrainee);

        Trainee createdTrainee = gymFacade.createTrainee("John", "Doe", "123 Main St", LocalDate.of(1990, 1, 1));

        assertNotNull(createdTrainee);
        assertEquals(testTrainee, createdTrainee);
        verify(traineeService).createTrainee("John", "Doe", "123 Main St", LocalDate.of(1990, 1, 1));
    }

    @Test
    void getTrainee_shouldCallTraineeService() {

        when(traineeService.read(testTraineeId)).thenReturn(testTrainee);

        Trainee foundTrainee = gymFacade.getTrainee(testTraineeId);

        assertNotNull(foundTrainee);
        assertEquals(testTrainee, foundTrainee);
        verify(traineeService).read(testTraineeId);
    }

    @Test
    void updateTrainee_shouldCallTraineeService() {

        when(traineeService.update(testTraineeId, testTrainee)).thenReturn(testTrainee);

        Trainee updatedTrainee = gymFacade.updateTrainee(testTraineeId, testTrainee);

        assertNotNull(updatedTrainee);
        assertEquals(testTrainee, updatedTrainee);
        verify(traineeService).update(testTraineeId, testTrainee);
    }

    @Test
    void deleteTrainee_shouldCallTraineeService() {

        when(traineeService.delete(testTraineeId)).thenReturn(true);

        boolean isDeleted = gymFacade.deleteTrainee(testTraineeId);

        assertTrue(isDeleted);
        verify(traineeService).delete(testTraineeId);
    }

    @Test
    void createTrainer_shouldCallTrainerService() {

        when(trainerService.createTrainer("Jane", "Smith", "Fitness"))
                .thenReturn(testTrainer);

        Trainer createdTrainer = gymFacade.createTrainer("Jane", "Smith", "Fitness");

        assertNotNull(createdTrainer);
        assertEquals(testTrainer, createdTrainer);
        verify(trainerService).createTrainer("Jane", "Smith", "Fitness");
    }

    @Test
    void getTrainer_shouldCallTrainerService() {

        when(trainerService.read(testTrainerId)).thenReturn(testTrainer);

        Trainer foundTrainer = gymFacade.getTrainer(testTrainerId);

        assertNotNull(foundTrainer);
        assertEquals(testTrainer, foundTrainer);
        verify(trainerService).read(testTrainerId);
    }

    @Test
    void updateTrainer_shouldCallTrainerService() {

        when(trainerService.update(testTrainerId, testTrainer)).thenReturn(testTrainer);

        Trainer updatedTrainer = gymFacade.updateTrainer(testTrainerId, testTrainer);

        assertNotNull(updatedTrainer);
        assertEquals(testTrainer, updatedTrainer);
        verify(trainerService).update(testTrainerId, testTrainer);
    }

    @Test
    void createTraining_shouldCallTrainingService() {

        when(trainingService.createTraining(testTraineeId, testTrainerId, "Morning Session", TrainingType.CARDIO, LocalDate.now(), 60))
                .thenReturn(testTraining);

        Training createdTraining = gymFacade.createTraining(testTraineeId, testTrainerId, "Morning Session", TrainingType.CARDIO, LocalDate.now(), 60);

        assertNotNull(createdTraining);
        assertEquals(testTraining, createdTraining);
        verify(trainingService).createTraining(testTraineeId, testTrainerId, "Morning Session", TrainingType.CARDIO, LocalDate.now(), 60);
    }

    @Test
    void getTraining_shouldCallTrainingService() {

        when(trainingService.read(testTrainingId)).thenReturn(testTraining);

        Training foundTraining = gymFacade.getTraining(testTrainingId);

        assertNotNull(foundTraining);
        assertEquals(testTraining, foundTraining);
        verify(trainingService).read(testTrainingId);
    }
}