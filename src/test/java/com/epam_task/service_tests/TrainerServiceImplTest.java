package com.epam_task.service_tests;

import com.epam_task.dao.TrainerDAO;
import com.epam_task.domain.Trainer;
import com.epam_task.service.TrainerServiceImpl;
import com.epam_task.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class TrainerServiceImplTest {

    @Mock
    private TrainerDAO trainerDAO;

    @Mock
    private UserService userService;

    @InjectMocks
    private TrainerServiceImpl trainerService;

    private Trainer testTrainer;

    @BeforeEach
    void setUp() {
        testTrainer = new Trainer();
        testTrainer.setUserId(UUID.randomUUID());
        testTrainer.setFirstName("John");
        testTrainer.setLastName("Doe");
        testTrainer.setSpecialization("Fitness");
        testTrainer.setUsername("john.doe");
        testTrainer.setPassword("password123");
        testTrainer.setIsActive(true);
    }

    @Test
    void createTrainer_shouldCreateTrainer() {

        when(userService.generateUsername("John", "Doe")).thenReturn("john.doe");
        when(userService.generateRandomPassword()).thenReturn("password123");
        when(trainerDAO.save(any(Trainer.class))).thenReturn(testTrainer);

        Trainer createdTrainer = trainerService.createTrainer("John", "Doe", "Fitness");

        assertNotNull(createdTrainer);
        assertEquals("John", createdTrainer.getFirstName());
        assertEquals("Doe", createdTrainer.getLastName());
        assertEquals("Fitness", createdTrainer.getSpecialization());
        assertEquals("john.doe", createdTrainer.getUsername());
        assertEquals("password123", createdTrainer.getPassword());
        assertTrue(createdTrainer.getIsActive());

        verify(userService).generateUsername("John", "Doe");
        verify(userService).generateRandomPassword();
        verify(trainerDAO).save(any(Trainer.class));
    }

    @Test
    void createTrainer_shouldThrowWhenRequiredFieldsAreNull() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> trainerService.createTrainer(null, "Doe", "Fitness"));

        assertEquals("All fields (firstName, lastName, specialization) are required.", exception.getMessage());

        verifyNoInteractions(userService, trainerDAO);
    }

    @Test
    void read_shouldReturnTrainer() {

        UUID trainerId = testTrainer.getUserId();
        when(trainerDAO.read(trainerId)).thenReturn(testTrainer);

        Trainer foundTrainer = trainerService.read(trainerId);

        assertNotNull(foundTrainer);
        assertEquals(trainerId, foundTrainer.getUserId());
        verify(trainerDAO).read(trainerId);
    }

    @Test
    void read_shouldThrowWhenIdIsNull() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> trainerService.read(null));

        assertEquals("Trainer ID cannot be null.", exception.getMessage());

        verifyNoInteractions(trainerDAO);
    }

    @Test
    void update_shouldUpdateTrainer() {

        UUID trainerId = testTrainer.getUserId();
        when(trainerDAO.update(trainerId, testTrainer)).thenReturn(testTrainer);

        Trainer updatedTrainer = trainerService.update(trainerId, testTrainer);

        assertNotNull(updatedTrainer);
        assertEquals(trainerId, updatedTrainer.getUserId());
        verify(trainerDAO).update(trainerId, testTrainer);
    }

    @Test
    void update_shouldThrowWhenIdOrTrainerIsNull() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> trainerService.update(null, testTrainer));

        assertEquals("Trainer ID and updated trainer details are required.", exception.getMessage());

        verifyNoInteractions(trainerDAO);
    }
}