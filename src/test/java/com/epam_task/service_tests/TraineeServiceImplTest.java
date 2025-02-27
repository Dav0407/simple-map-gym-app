package com.epam_task.service_tests;

import com.epam_task.dao.TraineeDAO;
import com.epam_task.domain.Trainee;
import com.epam_task.service.TraineeServiceImpl;
import com.epam_task.service.UserService;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class TraineeServiceImplTest {

    @Mock
    private TraineeDAO traineeDAO;

    @Mock
    private UserService userService;

    @InjectMocks
    private TraineeServiceImpl traineeService;

    private Trainee testTrainee;

    @BeforeEach
    void setUp() {
        // Initialize a test trainee object
        testTrainee = new Trainee();
        testTrainee.setUserId(UUID.randomUUID());
        testTrainee.setFirstName("John");
        testTrainee.setLastName("Doe");
        testTrainee.setAddress("123 Main St");
        testTrainee.setDate(LocalDate.of(1990, 1, 1));
        testTrainee.setUsername("john.doe");
        testTrainee.setPassword("password123");
        testTrainee.setIsActive(true);
    }

    @Test
    void createTrainee_shouldCreateTrainee() {

        when(userService.generateUsername("John", "Doe")).thenReturn("john.doe");
        when(userService.generateRandomPassword()).thenReturn("password123");
        when(traineeDAO.save(any(Trainee.class))).thenReturn(testTrainee);

        Trainee createdTrainee = traineeService.createTrainee("John", "Doe", "123 Main St", LocalDate.of(1990, 1, 1));

        assertNotNull(createdTrainee);
        assertEquals("John", createdTrainee.getFirstName());
        assertEquals("Doe", createdTrainee.getLastName());
        assertEquals("123 Main St", createdTrainee.getAddress());
        assertEquals(LocalDate.of(1990, 1, 1), createdTrainee.getDate());
        assertEquals("john.doe", createdTrainee.getUsername());
        assertEquals("password123", createdTrainee.getPassword());
        assertTrue(createdTrainee.getIsActive());

        verify(userService).generateUsername("John", "Doe");
        verify(userService).generateRandomPassword();
        verify(traineeDAO).save(any(Trainee.class));
    }

    @Test
    void createTrainee_shouldThrowWhenDateInFuture() {

        LocalDate futureDate = LocalDate.now().plusDays(1);


        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> traineeService.createTrainee("John", "Doe", "123 Main St", futureDate));

        assertEquals("Date of birth cannot be in the future.", exception.getMessage());

        verifyNoInteractions(userService, traineeDAO);
    }

    @Test
    void createTrainee_shouldThrowWhenRequiredFieldsAreNull() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> traineeService.createTrainee(null, "Doe", "123 Main St", LocalDate.of(1990, 1, 1)));

        assertEquals("All fields (firstName, lastName, address, date) are required.", exception.getMessage());

        verifyNoInteractions(userService, traineeDAO);
    }

    @Test
    void read_shouldReturnTrainee() {

        UUID traineeId = testTrainee.getUserId();
        when(traineeDAO.read(traineeId)).thenReturn(testTrainee);

        Trainee foundTrainee = traineeService.read(traineeId);

        assertNotNull(foundTrainee);
        assertEquals(traineeId, foundTrainee.getUserId());
        verify(traineeDAO).read(traineeId);
    }

    @Test
    void read_shouldThrowWhenIdIsNull() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> traineeService.read(null));

        assertEquals("Trainee ID cannot be null.", exception.getMessage());

        verifyNoInteractions(traineeDAO);
    }

    @Test
    void update_shouldUpdateTrainee() {

        UUID traineeId = testTrainee.getUserId();
        when(traineeDAO.update(traineeId, testTrainee)).thenReturn(testTrainee);

        Trainee updatedTrainee = traineeService.update(traineeId, testTrainee);

        assertNotNull(updatedTrainee);
        assertEquals(traineeId, updatedTrainee.getUserId());
        verify(traineeDAO).update(traineeId, testTrainee);
    }

    @Test
    void update_shouldThrowWhenIdOrTraineeIsNull() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> traineeService.update(null, testTrainee));

        assertEquals("Trainee ID and updated trainee details are required.", exception.getMessage());

        verifyNoInteractions(traineeDAO);
    }

    @Test
    void delete_shouldDeleteTrainee() {

        UUID traineeId = testTrainee.getUserId();
        when(traineeDAO.delete(traineeId)).thenReturn(true);

        boolean isDeleted = traineeService.delete(traineeId);

        assertTrue(isDeleted);
        verify(traineeDAO).delete(traineeId);
    }

    @Test
    void delete_shouldThrowWhenIdIsNull() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> traineeService.delete(null));

        assertEquals("Trainee ID cannot be null.", exception.getMessage());

        verifyNoInteractions(traineeDAO);
    }
}